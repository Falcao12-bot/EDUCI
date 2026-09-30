package com.example.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.*

/**
 * Professional Pedagogical Rich Content Editor for Course Creation.
 * Word/Google Docs level capabilities tailored for Ivorian educational curriculum.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichPedagogicalEditor(
    content: String,
    onContentChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Saisis ou conçois le contenu pédagogique du cours..."
) {
    // Internal TextFieldValue to preserve cursor position and selection
    var textFieldValue by remember(content) {
        // If content changed externally and doesn't match our current text, sync it
        mutableStateOf(TextFieldValue(text = content, selection = TextRange(content.length)))
    }

    // Undo / Redo history stacks
    val undoStack = remember { mutableStateListOf<String>() }
    val redoStack = remember { mutableStateListOf<String>() }

    fun updateContent(newText: String, newSelection: TextRange? = null) {
        if (newText != textFieldValue.text) {
            undoStack.add(textFieldValue.text)
            redoStack.clear()
            if (undoStack.size > 50) undoStack.removeAt(0)
            val sel = newSelection ?: TextRange(newText.length)
            textFieldValue = TextFieldValue(text = newText, selection = sel)
            onContentChange(newText)
        }
    }

    fun undo() {
        if (undoStack.isNotEmpty()) {
            val previous = undoStack.removeAt(undoStack.size - 1)
            redoStack.add(textFieldValue.text)
            textFieldValue = TextFieldValue(text = previous, selection = TextRange(previous.length))
            onContentChange(previous)
        }
    }

    fun redo() {
        if (redoStack.isNotEmpty()) {
            val next = redoStack.removeAt(redoStack.size - 1)
            undoStack.add(textFieldValue.text)
            textFieldValue = TextFieldValue(text = next, selection = TextRange(next.length))
            onContentChange(next)
        }
    }

    // Helper: Wrap selection or insert template
    fun wrapOrInsert(prefix: String, suffix: String, defaultPlaceholder: String) {
        val sel = textFieldValue.selection
        val text = textFieldValue.text
        if (sel.start != sel.end) {
            // Selected text: wrap it
            val selectedPart = text.substring(sel.start, sel.end)
            val newText = text.substring(0, sel.start) + prefix + selectedPart + suffix + text.substring(sel.end)
            val newCursorPos = sel.start + prefix.length + selectedPart.length + suffix.length
            updateContent(newText, TextRange(newCursorPos))
        } else {
            // No selection: insert default placeholder
            val insertText = prefix + defaultPlaceholder + suffix
            val newText = text.substring(0, sel.start) + insertText + text.substring(sel.start)
            val selectStart = sel.start + prefix.length
            val selectEnd = selectStart + defaultPlaceholder.length
            updateContent(newText, TextRange(selectStart, selectEnd))
        }
    }

    // Helper: Insert block at newline
    fun insertBlock(blockText: String) {
        val text = textFieldValue.text
        val sel = textFieldValue.selection
        val needsLeadingNewline = sel.start > 0 && !text.substring(0, sel.start).endsWith("\n\n")
        val prefix = if (needsLeadingNewline) "\n\n" else if (sel.start > 0 && !text.substring(0, sel.start).endsWith("\n")) "\n" else ""
        val fullInsert = prefix + blockText + "\n\n"
        val newText = text.substring(0, sel.start) + fullInsert + text.substring(sel.start)
        val newCursor = sel.start + fullInsert.length
        updateContent(newText, TextRange(newCursor))
    }

    // Dialog state controllers
    var activeDialog by remember { mutableStateOf<EditorDialogType?>(null) }
    var selectedToolbarTab by remember { mutableStateOf(0) }
    var showLivePreviewSplit by remember { mutableStateOf(false) }
    var showSearchReplaceBar by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var replaceQuery by remember { mutableStateOf("") }

    // Media photo picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            insertBlock("![Illustration pédagogique](${uri}|center|large)")
        }
    }

    // Real-time statistics
    val wordCount = remember(textFieldValue.text) {
        textFieldValue.text.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }.size
    }
    val charCount = remember(textFieldValue.text) { textFieldValue.text.length }
    val readingTimeMin = remember(wordCount) {
        maxOf(1, (wordCount / 130)) // average French reading speed for students
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // 1. Top Header Bar: Undo, Redo, Search, Stats, Preview Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                        onClick = { undo() },
                        enabled = undoStack.isNotEmpty(),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Undo,
                            contentDescription = "Annuler",
                            tint = if (undoStack.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                        )
                    }

                    IconButton(
                        onClick = { redo() },
                        enabled = redoStack.isNotEmpty(),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Redo,
                            contentDescription = "Rétablir",
                            tint = if (redoStack.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                        )
                    }

                    IconButton(
                        onClick = { showSearchReplaceBar = !showSearchReplaceBar },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FindReplace,
                            contentDescription = "Rechercher et remplacer",
                            tint = if (showSearchReplaceBar) EduCiGreenPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = {
                            activeDialog = EditorDialogType.ClearConfirm
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Tout effacer",
                            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                        )
                    }
                }

                // Stats & Live Preview Switch
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ) {
                        Text(
                            text = "$wordCount mots • ~${readingTimeMin} min",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    FilterChip(
                        selected = showLivePreviewSplit,
                        onClick = { showLivePreviewSplit = !showLivePreviewSplit },
                        label = { Text(if (showLivePreviewSplit) "Éditeur seul" else "Aperçu Direct", fontSize = 11.sp) },
                        leadingIcon = {
                            Icon(
                                if (showLivePreviewSplit) Icons.Default.EditNote else Icons.Default.Visibility,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            // Optional Search & Replace Bar
            AnimatedVisibility(visible = showSearchReplaceBar) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Rechercher...", fontSize = 12.sp) },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp)
                            )
                            OutlinedTextField(
                                value = replaceQuery,
                                onValueChange = { replaceQuery = it },
                                placeholder = { Text("Remplacer par...", fontSize = 12.sp) },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    if (searchQuery.isNotEmpty() && textFieldValue.text.contains(searchQuery)) {
                                        val replaced = textFieldValue.text.replace(searchQuery, replaceQuery)
                                        updateContent(replaced)
                                    }
                                },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Tout remplacer", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            // 2. Toolbar Category Tabs
            val toolbarTabs = listOf(
                "Texte & Style",
                "Structure & Listes",
                "Blocs Pédagogiques",
                "Tableaux",
                "Maths & Sciences",
                "Médias & Schémas"
            )
            val tabScroll = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .horizontalScroll(tabScroll)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                toolbarTabs.forEachIndexed { index, title ->
                    val isSelected = selectedToolbarTab == index
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) EduCiGreenPrimary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable { selectedToolbarTab = index }
                    ) {
                        Text(
                            text = title,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            // 3. Dynamic Toolbar Tools depending on Tab
            val toolsScroll = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(toolsScroll)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (selectedToolbarTab) {
                    0 -> {
                        // Texte & Style
                        ToolButton(label = "Gras", icon = Icons.Default.FormatBold) { wrapOrInsert("**", "**", "texte en gras") }
                        ToolButton(label = "Italique", icon = Icons.Default.FormatItalic) { wrapOrInsert("*", "*", "texte en italique") }
                        ToolButton(label = "Souligné", icon = Icons.Default.FormatUnderlined) { wrapOrInsert("__", "__", "texte souligné") }
                        ToolButton(label = "Barré", icon = Icons.Default.FormatStrikethrough) { wrapOrInsert("~~", "~~", "texte barré") }
                        ToolButton(label = "Surligner", icon = Icons.Default.BorderColor) { wrapOrInsert("==", "==", "notion clé") }
                        ToolButton(label = "Couleur", icon = Icons.Default.Palette) { activeDialog = EditorDialogType.TextColor }
                        ToolButton(label = "Retrait", icon = Icons.AutoMirrored.Filled.FormatIndentDecrease) {
                            val lines = textFieldValue.text.lines()
                            updateContent(lines.joinToString("\n") { if (it.startsWith("  ")) it.substring(2) else it })
                        }
                        ToolButton(label = "Indenter", icon = Icons.AutoMirrored.Filled.FormatIndentIncrease) {
                            wrapOrInsert("  ", "", "")
                        }
                        ToolButton(label = "Centrer", icon = Icons.Default.FormatAlignCenter) {
                            wrapOrInsert("<p align=\"center\">", "</p>", "Texte centré")
                        }
                        ToolButton(label = "Droite", icon = Icons.AutoMirrored.Filled.FormatAlignRight) {
                            wrapOrInsert("<p align=\"right\">", "</p>", "Texte à droite")
                        }
                    }

                    1 -> {
                        // Structure & Listes
                        ToolButton(label = "Titre 1 (H1)", icon = Icons.Default.Title) { insertBlock("# Grand Titre du Cours") }
                        ToolButton(label = "Titre 2 (H2)", icon = Icons.Default.FormatSize) { insertBlock("## Sous-Partie ou Chapitre") }
                        ToolButton(label = "Titre 3 (H3)", icon = Icons.AutoMirrored.Filled.Notes) { insertBlock("### Section Spécifique") }
                        ToolButton(label = "Paragraphe", icon = Icons.AutoMirrored.Filled.Subject) { insertBlock("Ce paragraphe développe une notion scolaire essentielle.") }
                        ToolButton(label = "Citation", icon = Icons.Default.FormatQuote) { insertBlock("> \"L'éducation est l'arme la plus puissante pour changer le monde.\" — Nelson Mandela") }
                        ToolButton(label = "Puces", icon = Icons.AutoMirrored.Filled.FormatListBulleted) { insertBlock("- Premier élément clé\n- Deuxième élément clé\n- Troisième élément clé") }
                        ToolButton(label = "Numérotée", icon = Icons.Default.FormatListNumbered) { insertBlock("1. Première étape méthodologique\n2. Deuxième étape de calcul\n3. Conclusion vérifiée") }
                        ToolButton(label = "Liste imbriquée", icon = Icons.AutoMirrored.Filled.PlaylistAdd) { insertBlock("- Notions Générales\n  - Sous-notion A\n  - Sous-notion B\n- Application pratique") }
                        ToolButton(label = "Définition simple", icon = Icons.AutoMirrored.Filled.MenuBook) { insertBlock("**Hypoténuse** : Le côté opposé à l'angle droit dans un triangle rectangle.") }
                        ToolButton(label = "Séparateur", icon = Icons.Default.HorizontalRule) { insertBlock("---") }
                    }

                    2 -> {
                        // Blocs Pédagogiques
                        ToolButton(label = "📖 Définition", color = EduCiGreenPrimary) {
                            insertBlock(":::definition\nUne grandeur est proportionnelle lorsque le rapport entre les valeurs est constant.\n:::")
                        }
                        ToolButton(label = "🎓 Théorème", color = Color(0xFF1D4ED8)) {
                            insertBlock(":::theoreme\nThéorème de Pythagore :\nDans un triangle rectangle, le carré de la longueur de l'hypoténuse est égal à la somme des carrés des longueurs des deux autres côtés.\n\$\$BC^2 = AB^2 + AC^2\$\$\n:::")
                        }
                        ToolButton(label = "📋 Propriété", color = Color(0xFF0F766E)) {
                            insertBlock(":::propriete\nSi deux droites sont perpendiculaires à une même troisième, alors elles sont parallèles entre elles.\n:::")
                        }
                        ToolButton(label = "💡 Exemple", color = Color(0xFF475569)) {
                            insertBlock(":::exemple\nCalculons l'hypoténuse d'un triangle ABC rectangle en A où AB = 3 cm et AC = 4 cm :\n\$\$BC^2 = 3^2 + 4^2 = 9 + 16 = 25 \\implies BC = 5\\text{ cm}\$\$\n:::")
                        }
                        ToolButton(label = "✏️ Exercice Flash", color = Color(0xFFC2410C)) {
                            insertBlock(":::exercice\nQuestion d'application :\nRésoudre dans ℝ l'équation suivante : 2x + 7 = 15.\n(Indication : soustraire 7 des deux côtés puis diviser par 2).\n:::")
                        }
                        ToolButton(label = "⚠️ Attention / Piège", color = Color(0xFFB91C1C)) {
                            insertBlock(":::attention\nErreur fréquente à ne jamais commettre :\n(a + b)² n'est PAS égal à a² + b² ! N'oubliez pas le double produit 2ab.\n:::")
                        }
                        ToolButton(label = "✨ Conseil Prof", color = Color(0xFFB45309)) {
                            insertBlock(":::conseil\nPour gagner des points à l'examen, rédigez toujours la formule littérale avant d'effectuer l'application numérique.\n:::")
                        }
                        ToolButton(label = "🧠 Méthode", color = Color(0xFF7E22CE)) {
                            insertBlock(":::methode\nMéthode pas à pas pour équilibrer une réaction chimique :\n1. Identifier les réactifs et les produits.\n2. Compter chaque type d'atome de chaque côté.\n3. Ajuster les coefficients stœchiométriques.\n:::")
                        }
                        ToolButton(label = "📐 Formule Clé", color = Color(0xFF047857)) {
                            insertBlock(":::formule\nVitesse moyenne : \$\$v = \\frac{d}{t}\$\$\navec \$d\$ la distance parcourue en mètres et \$t\$ le temps en secondes.\n:::")
                        }
                        ToolButton(label = "📌 À Retenir", color = EduCiGreenDark) {
                            insertBlock(":::retenir\nSynthèse du cours :\n• Retenir les 3 propriétés fondamentales.\n• Maîtriser le calcul de proportionnalité.\n• Refaire les 2 exercices d'entraînement avant le contrôle.\n:::")
                        }
                    }

                    3 -> {
                        // Tableaux
                        ToolButton(label = "Assistant Tableau", icon = Icons.Default.TableChart, color = EduCiGreenPrimary) {
                            activeDialog = EditorDialogType.TableBuilder
                        }
                        ToolButton(label = "Tableau 2x3 rapide") {
                            insertBlock(
                                "| Grandeur / Caractéristique | Valeur 1 | Valeur 2 |\n" +
                                "| --- | --- | --- |\n" +
                                "| Masse | 2 kg | 4 kg |\n" +
                                "| Prix unitaire | 1 500 FCFA | 3 000 FCFA |"
                            )
                        }
                        ToolButton(label = "Tableau de Signes") {
                            insertBlock(
                                "| x | -∞ | 2 | +∞ |\n" +
                                "| --- | --- | --- | --- |\n" +
                                "| Signe de (x - 2) | - | 0 | + |"
                            )
                        }
                        ToolButton(label = "Tableau de Données") {
                            insertBlock(
                                "| Étape | Description | Résultat attendu |\n" +
                                "| --- | --- | --- |\n" +
                                "| Étape 1 | Prélèvement de 10 mL | Solution prête |\n" +
                                "| Étape 2 | Chauffage au bain-marie | Virage de couleur |"
                            )
                        }
                    }

                    4 -> {
                        // Maths & Sciences
                        ToolButton(label = "Assistant Formules", icon = Icons.Default.Calculate, color = Color(0xFF0F766E)) {
                            activeDialog = EditorDialogType.MathBuilder
                        }
                        ToolButton(label = "Fraction \\frac{a}{b}") { wrapOrInsert("\$\$\\frac{", "}{d}\$\$", "numérateur") }
                        ToolButton(label = "Puissance x^n") { wrapOrInsert("\$", "^2\$", "x") }
                        ToolButton(label = "Racine \\sqrt{x}") { wrapOrInsert("\$\$\\sqrt{", "}\$\$", "x + 1") }
                        ToolButton(label = "Indice x_i") { wrapOrInsert("\$", "_1\$", "x") }
                        ToolButton(label = "Équation du 2nd degré") { insertBlock("\$\$ax^2 + bx + c = 0\$\$") }
                        ToolButton(label = "Théorème de Thalès") { insertBlock("\$\$\\frac{AM}{AB} = \\frac{AN}{AC} = \\frac{MN}{BC}\$\$") }
                        ToolButton(label = "Symboles grecs") { activeDialog = EditorDialogType.GreekSymbols }
                        ToolButton(label = "Inégalités (≤, ≥, ≠)") { wrapOrInsert("\$x ", " 0\$", "\\ge") }
                    }

                    5 -> {
                        // Médias & Schémas / Figures Géométriques
                        ToolButton(label = "Importer Image (Appareil)", icon = Icons.Default.AddPhotoAlternate, color = EduCiGreenPrimary) {
                            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                        ToolButton(label = "Insérer Image par URL", icon = Icons.Default.Link) {
                            activeDialog = EditorDialogType.ImageUrlInsert
                        }
                        ToolButton(label = "Figure Géométrique", icon = Icons.Default.Category, color = Color(0xFFD97706)) {
                            activeDialog = EditorDialogType.GeometryFigure
                        }
                        ToolButton(label = "Triangle Rectangle") {
                            insertBlock(":::figure:triangle\nTriangle rectangle ABC en A\nAB = 3 cm, AC = 4 cm, BC = 5 cm (hypoténuse codée)\n:::")
                        }
                        ToolButton(label = "Cercle Trigonométrique") {
                            insertBlock(":::figure:cercle\nCercle de centre O et de rayon R\nRayon r = 5 cm, diamètre D = 10 cm\n:::")
                        }
                        ToolButton(label = "Repère Orthonormé") {
                            insertBlock(":::figure:repere\nRepère cartésien orthonormé (O, I, J)\nGraduations unitaires sur les axes (Ox) et (Oy)\n:::")
                        }
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            // 4. Content Area: Split View or Full Editor
            if (showLivePreviewSplit) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 320.dp, max = 500.dp)
                ) {
                    // Editor Side
                    Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                        OutlinedTextField(
                            value = textFieldValue,
                            onValueChange = {
                                textFieldValue = it
                                onContentChange(it.text)
                            },
                            placeholder = { Text(placeholder, fontSize = 13.sp) },
                            modifier = Modifier
                                .fillMaxSize()
                                .testTag("rich_editor_textarea_split"),
                            shape = RoundedCornerShape(0.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }

                    VerticalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

                    // Real-time Preview Side
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                            .padding(12.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Column {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = EduCiGreenContainer,
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = "👁️ Aperçu élève direct :",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EduCiGreenDark,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            RichContentRenderer(content = textFieldValue.text)
                        }
                    }
                }
            } else {
                // Fullscreen Editor
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                        onContentChange(it.text)
                    },
                    placeholder = { Text(placeholder, fontSize = 13.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 280.dp, max = 550.dp)
                        .testTag("rich_editor_textarea"),
                    shape = RoundedCornerShape(0.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }
        }
    }

    // --- Interactive Dialogs ---

    when (activeDialog) {
        EditorDialogType.TableBuilder -> {
            TableBuilderDialog(
                onDismiss = { activeDialog = null },
                onInsertTable = { markdownTable ->
                    insertBlock(markdownTable)
                    activeDialog = null
                }
            )
        }

        EditorDialogType.MathBuilder -> {
            MathBuilderDialog(
                onDismiss = { activeDialog = null },
                onInsertFormula = { formula, asBlock ->
                    if (asBlock) {
                        insertBlock("$$\n$formula\n$$")
                    } else {
                        wrapOrInsert("$", "$", formula)
                    }
                    activeDialog = null
                }
            )
        }

        EditorDialogType.GreekSymbols -> {
            GreekSymbolsDialog(
                onDismiss = { activeDialog = null },
                onSelectSymbol = { symbol ->
                    wrapOrInsert("$", "$", symbol)
                    activeDialog = null
                }
            )
        }

        EditorDialogType.ImageUrlInsert -> {
            ImageUrlInsertDialog(
                onDismiss = { activeDialog = null },
                onInsertImage = { url, caption, align, size ->
                    insertBlock("![$caption]($url|$align|$size)")
                    activeDialog = null
                }
            )
        }

        EditorDialogType.GeometryFigure -> {
            GeometryFigureDialog(
                onDismiss = { activeDialog = null },
                onInsertFigure = { type, title, desc ->
                    insertBlock(":::figure:$type\n$title\n$desc\n:::")
                    activeDialog = null
                }
            )
        }

        EditorDialogType.TextColor -> {
            TextColorDialog(
                onDismiss = { activeDialog = null },
                onSelectColor = { colorHex ->
                    wrapOrInsert("<color:$colorHex>", "</color>", "texte coloré")
                    activeDialog = null
                }
            )
        }

        EditorDialogType.ClearConfirm -> {
            AlertDialog(
                onDismissRequest = { activeDialog = null },
                title = { Text("Effacer tout le contenu ?") },
                text = { Text("Cette action effacera l'ensemble du texte de l'éditeur. Tu pourras utiliser 'Annuler' si nécessaire.") },
                confirmButton = {
                    Button(
                        onClick = {
                            updateContent("")
                            activeDialog = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Effacer tout")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { activeDialog = null }) { Text("Annuler") }
                }
            )
        }

        null -> {}
    }
}

private enum class EditorDialogType {
    TableBuilder,
    MathBuilder,
    GreekSymbols,
    ImageUrlInsert,
    GeometryFigure,
    TextColor,
    ClearConfirm
}

@Composable
private fun ToolButton(
    label: String,
    icon: ImageVector? = null,
    color: Color? = null,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = color?.copy(alpha = 0.12f) ?: MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = color ?: MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = color ?: MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// -------------------------------------------------------------
// Interactive Table Builder Dialog
// -------------------------------------------------------------
@Composable
private fun TableBuilderDialog(
    onDismiss: () -> Unit,
    onInsertTable: (String) -> Unit
) {
    var numCols by remember { mutableStateOf(3) }
    var numRows by remember { mutableStateOf(2) }

    val headers = remember { mutableStateListOf("Grandeur / Critère", "Valeur 1", "Valeur 2") }
    val rowData = remember {
        mutableStateListOf(
            mutableStateListOf("Masse", "2 kg", "4 kg"),
            mutableStateListOf("Prix", "1 500 F", "3 000 F")
        )
    }

    // Sync column counts
    fun adjustCols(newCols: Int) {
        if (newCols in 1..6) {
            numCols = newCols
            while (headers.size < newCols) headers.add("Colonne ${headers.size + 1}")
            while (headers.size > newCols) headers.removeAt(headers.size - 1)
            for (r in rowData) {
                while (r.size < newCols) r.add("Donnée")
                while (r.size > newCols) r.removeAt(r.size - 1)
            }
        }
    }

    fun addRow() {
        if (numRows < 8) {
            numRows++
            val newRow = mutableStateListOf<String>()
            for (i in 0 until numCols) newRow.add("Valeur")
            rowData.add(newRow)
        }
    }

    fun removeRow() {
        if (numRows > 1) {
            numRows--
            rowData.removeAt(rowData.size - 1)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.TableChart, contentDescription = null, tint = EduCiGreenPrimary)
                Text("Créer un tableau pédagogique", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Configure le nombre de colonnes et de lignes, puis modifie directement les en-têtes et données ci-dessous :",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Controls: Columns & Rows
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Colonnes :", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        IconButton(onClick = { adjustCols(numCols - 1) }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.Remove, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                        Text("$numCols", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        IconButton(onClick = { adjustCols(numCols + 1) }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Lignes :", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        IconButton(onClick = { removeRow() }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.Remove, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                        Text("$numRows", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        IconButton(onClick = { addRow() }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                // Table Grid inputs
                val hScroll = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .horizontalScroll(hScroll)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Headers row
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        for (c in 0 until numCols) {
                            OutlinedTextField(
                                value = headers.getOrElse(c) { "" },
                                onValueChange = { headers[c] = it },
                                label = { Text("Col ${c + 1}", fontSize = 10.sp) },
                                modifier = Modifier.width(130.dp),
                                singleLine = true,
                                shape = RoundedCornerShape(6.dp)
                            )
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

                    // Data rows
                    for (r in 0 until numRows) {
                        val row = rowData.getOrNull(r) ?: continue
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            for (c in 0 until numCols) {
                                OutlinedTextField(
                                    value = row.getOrElse(c) { "" },
                                    onValueChange = { row[c] = it },
                                    modifier = Modifier.width(130.dp),
                                    singleLine = true,
                                    shape = RoundedCornerShape(6.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val sb = StringBuilder()
                    // Header line
                    sb.append("| ").append(headers.joinToString(" | ")).append(" |\n")
                    // Separator line
                    sb.append("| ").append(headers.map { "---" }.joinToString(" | ")).append(" |\n")
                    // Data rows
                    for (row in rowData) {
                        sb.append("| ").append(row.joinToString(" | ")).append(" |\n")
                    }
                    onInsertTable(sb.toString().trim())
                },
                colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
            ) {
                Text("Insérer dans le cours")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annuler") }
        }
    )
}

// -------------------------------------------------------------
// Interactive Math Formula Builder Dialog
// -------------------------------------------------------------
@Composable
private fun MathBuilderDialog(
    onDismiss: () -> Unit,
    onInsertFormula: (formula: String, asBlock: Boolean) -> Unit
) {
    var formulaText by remember { mutableStateOf("\\frac{a}{b} + \\sqrt{x^2 + 1} = 0") }
    var asBlock by remember { mutableStateOf(true) }

    val presetFormulas = listOf(
        "Fraction" to "\\frac{a}{b}",
        "Racine carrée" to "\\sqrt{x}",
        "Puissance" to "x^2 + y^2 = r^2",
        "Équation 2nd degré" to "ax^2 + bx + c = 0",
        "Discriminant Delta" to "\\Delta = b^2 - 4ac",
        "Théorème Pythagore" to "BC^2 = AB^2 + AC^2",
        "Vitesse" to "v = \\frac{d}{t}",
        "Masse volumique" to "\\rho = \\frac{m}{V}",
        "Loi d'Ohm" to "U = R \\times I",
        "Somme" to "\\sum_{i=1}^{n} x_i",
        "Intégrale" to "\\int_{a}^{b} f(x)dx"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Functions, contentDescription = null, tint = EduCiGreenPrimary)
                Text("Assistant Formules Mathématiques", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Choisis un modèle ou compose directement ta formule en notation standard :",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Quick presets
                val presetScroll = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(presetScroll),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for ((name, code) in presetFormulas) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.clickable { formulaText = code }
                        ) {
                            Text(
                                text = name,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = formulaText,
                    onValueChange = { formulaText = it },
                    label = { Text("Code de la formule") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(fontFamily = FontFamily.Monospace, fontSize = 14.sp)
                )

                // Live Formula Preview
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF8FAFC),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Aperçu visuel :", fontSize = 10.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = formulaText.ifEmpty { "..." },
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF0F172A)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = asBlock, onCheckedChange = { asBlock = it })
                    Text(
                        text = if (asBlock) "Afficher en bloc centré (\$\$...\$\$)" else "Afficher en ligne dans le texte (\$...\$)",
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onInsertFormula(formulaText, asBlock) },
                colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
            ) {
                Text("Insérer la formule")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annuler") }
        }
    )
}

// -------------------------------------------------------------
// Greek & Scientific Symbols Dialog
// -------------------------------------------------------------
@Composable
private fun GreekSymbolsDialog(
    onDismiss: () -> Unit,
    onSelectSymbol: (String) -> Unit
) {
    val symbols = listOf(
        "\\alpha" to "α (alpha)",
        "\\beta" to "β (bêta)",
        "\\gamma" to "γ (gamma)",
        "\\Delta" to "Δ (Delta)",
        "\\theta" to "θ (thêta)",
        "\\lambda" to "λ (lambda)",
        "\\mu" to "μ (micro)",
        "\\pi" to "π (pi)",
        "\\sigma" to "σ (sigma)",
        "\\omega" to "ω (oméga)",
        "\\Omega" to "Ω (Ohm)",
        "\\le" to "≤ (inf. ou égal)",
        "\\ge" to "≥ (sup. ou égal)",
        "\\neq" to "≠ (différent)",
        "\\approx" to "≈ (environ)",
        "\\pm" to "± (plus ou moins)",
        "\\infty" to "∞ (infini)",
        "\\in" to "∈ (appartient à)",
        "\\cup" to "∪ (union)",
        "\\cap" to "∩ (intersection)"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Lettres Grecques & Symboles") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Clique sur un symbole pour l'insérer dans ton cours :", fontSize = 12.sp)

                symbols.chunked(2).forEach { row ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        for ((code, display) in row) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onSelectSymbol(code) }
                            ) {
                                Text(
                                    text = display,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Fermer") }
        }
    )
}

// -------------------------------------------------------------
// Image URL & Custom Legend Dialog
// -------------------------------------------------------------
@Composable
private fun ImageUrlInsertDialog(
    onDismiss: () -> Unit,
    onInsertImage: (url: String, caption: String, align: String, size: String) -> Unit
) {
    var url by remember { mutableStateOf("https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=600") }
    var caption by remember { mutableStateOf("Schéma explicatif de géométrie dans l'espace") }
    var align by remember { mutableStateOf("center") }
    var size by remember { mutableStateOf("large") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Insérer une image Web ou Hébergée") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("URL de l'image (HTTPS)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = caption,
                    onValueChange = { caption = it },
                    label = { Text("Légende pour l'élève (optionnelle)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                // Align buttons
                Text("Alignement :", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = align == "left",
                        onClick = { align = "left" },
                        label = { Text("Gauche") }
                    )
                    FilterChip(
                        selected = align == "center",
                        onClick = { align = "center" },
                        label = { Text("Centré") }
                    )
                    FilterChip(
                        selected = align == "right",
                        onClick = { align = "right" },
                        label = { Text("Droite") }
                    )
                }

                // Image Preview
                if (url.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF1F5F9)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(url).crossfade(true).build(),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onInsertImage(url, caption, align, size) },
                enabled = url.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
            ) {
                Text("Insérer l'image")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annuler") }
        }
    )
}

// -------------------------------------------------------------
// Geometric Figures Dialog
// -------------------------------------------------------------
@Composable
private fun GeometryFigureDialog(
    onDismiss: () -> Unit,
    onInsertFigure: (type: String, title: String, desc: String) -> Unit
) {
    var selectedType by remember { mutableStateOf("triangle") }
    var title by remember { mutableStateOf("Triangle rectangle ABC") }
    var description by remember { mutableStateOf("Angle droit en A avec hypoténuse BC = 5 cm") }

    val types = listOf(
        "triangle" to "Triangle Rectangle",
        "cercle" to "Cercle & Rayon",
        "rectangle" to "Rectangle & Dimensions",
        "repere" to "Repère Orthonormé (O,I,J)",
        "cylindre" to "Cylindre de Révolution"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Insérer une figure géométrique dynamique") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Sélectionne le type de figure pédagogique à tracer :", fontSize = 12.sp)

                types.forEach { (typeId, typeName) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedType = typeId
                                title = typeName
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedType == typeId,
                            onClick = {
                                selectedType = typeId
                                title = typeName
                            }
                        )
                        Text(typeName, fontSize = 13.sp)
                    }
                }

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Titre de la figure") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description / Propriété géométrique") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onInsertFigure(selectedType, title, description) },
                colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
            ) {
                Text("Insérer la figure")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annuler") }
        }
    )
}

// -------------------------------------------------------------
// Text Color Palette Dialog
// -------------------------------------------------------------
@Composable
private fun TextColorDialog(
    onDismiss: () -> Unit,
    onSelectColor: (hex: String) -> Unit
) {
    val colors = listOf(
        "#0F766E" to "Vert Émeraude",
        "#1D4ED8" to "Bleu Cobalt",
        "#B91C1C" to "Rouge Vif",
        "#C2410C" to "Orange Ivoirien",
        "#7E22CE" to "Violet Profond",
        "#0F172A" to "Noir Intense",
        "#475569" to "Gris Ardoise",
        "#D97706" to "Ambre Solaire"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Choisir une couleur de texte") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                colors.forEach { (hex, name) ->
                    val color = try {
                        val cLong = hex.removePrefix("#").toLong(16)
                        Color(cLong or 0x00000000FF000000)
                    } catch (e: Exception) {
                        Color.Black
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onSelectColor(hex) }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(1.dp, Color.Gray.copy(alpha = 0.3f), CircleShape)
                        )
                        Text(name, fontWeight = FontWeight.Medium, color = color)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annuler") }
        }
    )
}
