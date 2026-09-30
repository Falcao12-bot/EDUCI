package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.*

@Composable
fun RichContentRenderer(
    content: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val blocks = parseContentBlocks(content)
        for (block in blocks) {
            when (block) {
                is ContentBlock.Heading1 -> {
                    Text(
                        text = block.text,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
                    )
                }
                is ContentBlock.Heading2 -> {
                    Text(
                        text = block.text,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 17.sp
                        ),
                        modifier = Modifier.padding(top = 6.dp, bottom = 2.dp)
                    )
                }
                is ContentBlock.Heading3 -> {
                    Text(
                        text = block.text,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 15.sp
                        )
                    )
                }
                is ContentBlock.Callout -> {
                    CalloutBox(type = block.type, text = block.text)
                }
                is ContentBlock.MathDisplay -> {
                    MathDisplayCard(formula = block.formula)
                }
                is ContentBlock.Table -> {
                    MarkdownTableRenderer(tableData = block)
                }
                is ContentBlock.ImageBlock -> {
                    ImageContentCard(image = block)
                }
                is ContentBlock.GeometricFigure -> {
                    GeometricFigureCard(figure = block)
                }
                is ContentBlock.Blockquote -> {
                    BlockquoteCard(quote = block.text)
                }
                is ContentBlock.BulletItem -> {
                    val indentPadding = (block.indentLevel * 14).dp
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp + indentPadding),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = if (block.indentLevel > 0) "◦ " else "• ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 15.sp
                        )
                        FormattedText(text = block.text)
                    }
                }
                is ContentBlock.NumberedItem -> {
                    val indentPadding = (block.indentLevel * 14).dp
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp + indentPadding),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "${block.number}. ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 15.sp
                        )
                        FormattedText(text = block.text)
                    }
                }
                is ContentBlock.Paragraph -> {
                    val align = when (block.alignment) {
                        "center" -> TextAlign.Center
                        "right" -> TextAlign.Right
                        "justify" -> TextAlign.Justify
                        else -> TextAlign.Start
                    }
                    FormattedText(text = block.text, textAlign = align)
                }
                is ContentBlock.Divider -> {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}

@Composable
fun FormattedText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    val annotated = buildAnnotatedString {
        var currentIndex = 0
        // Match bold (**text**), italic (*text*), underline (__text__), strikethrough (~~text~~),
        // highlight (==text==), color (<color:#HEX>text</color>), display math ($$text$$), inline math ($text$)
        val regex = Regex(
            """(\*\*([^*]+)\*\*)|(\*([^*]+)\*)|(__([^_]+)__)|(~~([^~]+)~~)|(==([^=]+)==)|(<color:([#A-Za-z0-9]+)>([^<]+)</color>)|(\$\$([^$]+)\$\$)|(\$([^$]+)\$)"""
        )
        val matches = regex.findAll(text)

        for (match in matches) {
            val range = match.range
            if (range.first > currentIndex) {
                append(text.substring(currentIndex, range.first))
            }

            when {
                // Bold: **text**
                match.groupValues[1].isNotEmpty() -> {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(match.groupValues[2])
                    }
                }
                // Italic: *text*
                match.groupValues[3].isNotEmpty() -> {
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(match.groupValues[4])
                    }
                }
                // Underline: __text__
                match.groupValues[5].isNotEmpty() -> {
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(match.groupValues[6])
                    }
                }
                // Strikethrough: ~~text~~
                match.groupValues[7].isNotEmpty() -> {
                    withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                        append(match.groupValues[8])
                    }
                }
                // Highlight: ==text==
                match.groupValues[9].isNotEmpty() -> {
                    withStyle(
                        SpanStyle(
                            background = Color(0xFFFEF08A), // Warm highlighter yellow
                            color = Color(0xFF713F12),
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append(" ${match.groupValues[10]} ")
                    }
                }
                // Color: <color:#HEX>text</color>
                match.groupValues[11].isNotEmpty() -> {
                    val hexStr = match.groupValues[12]
                    val parsedColor = try {
                        val cleanHex = hexStr.removePrefix("#")
                        val colorLong = cleanHex.toLong(16)
                        if (cleanHex.length == 6) Color(colorLong or 0x00000000FF000000) else Color(colorLong)
                    } catch (e: Exception) {
                        Color.Unspecified
                    }
                    withStyle(SpanStyle(color = parsedColor)) {
                        append(match.groupValues[13])
                    }
                }
                // Display Math: $$formula$$
                match.groupValues[14].isNotEmpty() -> {
                    withStyle(
                        SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            color = EduCiGreenDark,
                            background = EduCiGreenContainer.copy(alpha = 0.5f)
                        )
                    ) {
                        append(" ${match.groupValues[15]} ")
                    }
                }
                // Inline Math: $formula$
                match.groupValues[16].isNotEmpty() -> {
                    withStyle(
                        SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Medium,
                            color = EduCiGreenDark,
                            background = EduCiGreenContainer.copy(alpha = 0.4f)
                        )
                    ) {
                        append(" ${match.groupValues[17]} ")
                    }
                }
            }
            currentIndex = range.last + 1
        }

        if (currentIndex < text.length) {
            append(text.substring(currentIndex))
        }
    }

    Text(
        text = annotated,
        style = MaterialTheme.typography.bodyMedium.copy(
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = textAlign
        ),
        modifier = modifier
    )
}

@Composable
fun CalloutBox(type: String, text: String) {
    val (bgColor, borderColor, icon, title, titleColor) = when (type.lowercase()) {
        "definition", "définition" -> CalloutConfig(
            bgColor = Color(0xFFF0FDF4),
            borderColor = EduCiGreenLight,
            icon = Icons.AutoMirrored.Filled.MenuBook,
            title = "DÉFINITION",
            titleColor = EduCiGreenPrimary
        )
        "theoreme", "théorème", "théoréme" -> CalloutConfig(
            bgColor = Color(0xFFEFF6FF),
            borderColor = Color(0xFF3B82F6),
            icon = Icons.Default.School,
            title = "THÉORÈME",
            titleColor = Color(0xFF1D4ED8)
        )
        "propriete", "propriété" -> CalloutConfig(
            bgColor = Color(0xFFF0FDFA),
            borderColor = Color(0xFF14B8A6),
            icon = Icons.Default.Checklist,
            title = "PROPRIÉTÉ FONDAMENTALE",
            titleColor = Color(0xFF0F766E)
        )
        "exemple" -> CalloutConfig(
            bgColor = Color(0xFFF8FAFC),
            borderColor = Color(0xFF94A3B8),
            icon = Icons.Default.Lightbulb,
            title = "EXEMPLE CONCRET",
            titleColor = Color(0xFF334155)
        )
        "exercice", "application" -> CalloutConfig(
            bgColor = Color(0xFFFFF7ED),
            borderColor = Color(0xFFF97316),
            icon = Icons.Default.EditNote,
            title = "APPLICATION / EXERCICE RAPIDE",
            titleColor = Color(0xFFC2410C)
        )
        "attention", "piege", "piège" -> CalloutConfig(
            bgColor = Color(0xFFFEF2F2),
            borderColor = Color(0xFFF87171),
            icon = Icons.Default.Warning,
            title = "ATTENTION / PIÈGE COURANT",
            titleColor = Color(0xFFB91C1C)
        )
        "conseil", "astuce" -> CalloutConfig(
            bgColor = Color(0xFFFFFBEB),
            borderColor = Color(0xFFFBBF24),
            icon = Icons.Default.TipsAndUpdates,
            title = "CONSEIL DU PROFESSEUR",
            titleColor = Color(0xFFB45309)
        )
        "methode", "méthode" -> CalloutConfig(
            bgColor = Color(0xFFFAF5FF),
            borderColor = Color(0xFFA855F7),
            icon = Icons.Default.Psychology,
            title = "MÉTHODE DE RÉSOLUTION",
            titleColor = Color(0xFF7E22CE)
        )
        "formule", "formule-cle" -> CalloutConfig(
            bgColor = Color(0xFFECFDF5),
            borderColor = Color(0xFF10B981),
            icon = Icons.Default.Functions,
            title = "FORMULE CLÉ",
            titleColor = Color(0xFF047857)
        )
        "retenir", "synthese", "synthèse" -> CalloutConfig(
            bgColor = Color(0xFFF0FDF4),
            borderColor = EduCiGreenPrimary,
            icon = Icons.Default.Bookmark,
            title = "À RETENIR ESSENTIEL",
            titleColor = EduCiGreenDark
        )
        else -> CalloutConfig(
            bgColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            borderColor = MaterialTheme.colorScheme.outline,
            icon = Icons.Default.Info,
            title = "NOTE",
            titleColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(
            modifier = Modifier
                .border(1.dp, borderColor.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = titleColor,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = titleColor,
                    letterSpacing = 0.5.sp
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            FormattedText(text = text)
        }
    }
}

private data class CalloutConfig(
    val bgColor: Color,
    val borderColor: Color,
    val icon: ImageVector,
    val title: String,
    val titleColor: Color
)

@Composable
fun BlockquoteCard(quote: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(36.dp)
                .background(EduCiGreenPrimary, RoundedCornerShape(2.dp))
        )
        Column {
            Icon(Icons.Default.FormatQuote, contentDescription = null, tint = EduCiGreenPrimary, modifier = Modifier.size(18.dp))
            FormattedText(text = quote, modifier = Modifier.padding(top = 2.dp))
        }
    }
}

@Composable
fun MathDisplayCard(formula: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
    ) {
        Column(
            modifier = Modifier
                .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Icon(Icons.Default.Functions, contentDescription = null, tint = Color(0xFF0F766E), modifier = Modifier.size(16.dp))
                Text("EXPRESSION MATHÉMATIQUE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F766E), letterSpacing = 0.5.sp)
            }

            Text(
                text = formula,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ImageContentCard(image: ContentBlock.ImageBlock) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                .padding(8.dp),
            horizontalAlignment = when (image.alignment) {
                "center" -> Alignment.CenterHorizontally
                "right" -> Alignment.End
                else -> Alignment.Start
            }
        ) {
            val context = LocalContext.current
            val imageModifier = when (image.size) {
                "small" -> Modifier
                    .width(180.dp)
                    .height(120.dp)
                "medium" -> Modifier
                    .fillMaxWidth(0.75f)
                    .height(180.dp)
                else -> Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            }

            Box(
                modifier = imageModifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF1F5F9)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(image.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = image.caption.ifEmpty { "Illustration du cours" },
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }

            if (image.caption.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "📷 ${image.caption}",
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
fun GeometricFigureCard(figure: ContentBlock.GeometricFigure) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
    ) {
        Column(
            modifier = Modifier
                .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(12.dp))
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Icon(Icons.Default.Category, contentDescription = null, tint = EduCiGreenPrimary, modifier = Modifier.size(16.dp))
                Text(
                    text = "FIGURE GÉOMÉTRIQUE : ${figure.title.uppercase()}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = EduCiGreenDark,
                    letterSpacing = 0.5.sp
                )
            }

            // Draw Canvas figure
            Box(
                modifier = Modifier
                    .size(240.dp, 160.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    val primaryColor = Color(0xFF0F766E)
                    val accentColor = Color(0xFFF97316)
                    val strokeWidth = 3f

                    when (figure.type.lowercase()) {
                        "triangle" -> {
                            val path = Path().apply {
                                moveTo(size.width * 0.2f, size.height * 0.85f)
                                lineTo(size.width * 0.85f, size.height * 0.85f)
                                lineTo(size.width * 0.2f, size.height * 0.15f)
                                close()
                            }
                            drawPath(path, color = primaryColor, style = Stroke(width = strokeWidth))
                            // Right angle symbol at bottom-left
                            val squareSize = 16f
                            val anglePath = Path().apply {
                                moveTo(size.width * 0.2f, size.height * 0.85f - squareSize)
                                lineTo(size.width * 0.2f + squareSize, size.height * 0.85f - squareSize)
                                lineTo(size.width * 0.2f + squareSize, size.height * 0.85f)
                            }
                            drawPath(anglePath, color = accentColor, style = Stroke(width = 2f))
                        }
                        "cercle", "circle" -> {
                            val center = Offset(size.width / 2, size.height / 2)
                            val radius = size.height * 0.4f
                            drawCircle(color = primaryColor, radius = radius, center = center, style = Stroke(width = strokeWidth))
                            // Center dot
                            drawCircle(color = accentColor, radius = 4f, center = center)
                            // Radius line
                            drawLine(
                                color = accentColor,
                                start = center,
                                end = Offset(center.x + radius, center.y),
                                strokeWidth = 2f
                            )
                        }
                        "rectangle" -> {
                            drawRect(
                                color = primaryColor,
                                topLeft = Offset(size.width * 0.15f, size.height * 0.25f),
                                size = Size(size.width * 0.7f, size.height * 0.5f),
                                style = Stroke(width = strokeWidth)
                            )
                        }
                        "repere", "axes" -> {
                            val cx = size.width / 2
                            val cy = size.height / 2
                            // X axis
                            drawLine(color = Color.DarkGray, start = Offset(10f, cy), end = Offset(size.width - 10f, cy), strokeWidth = 2f)
                            // Y axis
                            drawLine(color = Color.DarkGray, start = Offset(cx, size.height - 10f), end = Offset(cx, 10f), strokeWidth = 2f)
                            // Origin dot
                            drawCircle(color = accentColor, radius = 4f, center = Offset(cx, cy))
                            // Grid ticks
                            for (tick in -3..3) {
                                if (tick != 0) {
                                    val tx = cx + tick * (size.width / 8)
                                    drawLine(color = Color.Gray, start = Offset(tx, cy - 5), end = Offset(tx, cy + 5), strokeWidth = 1.5f)
                                }
                            }
                        }
                        "cylindre" -> {
                            val topCenter = Offset(size.width / 2, size.height * 0.25f)
                            val botCenter = Offset(size.width / 2, size.height * 0.75f)
                            val rx = size.width * 0.25f
                            val ry = size.height * 0.12f

                            // Body lines
                            drawLine(color = primaryColor, start = Offset(topCenter.x - rx, topCenter.y), end = Offset(botCenter.x - rx, botCenter.y), strokeWidth = strokeWidth)
                            drawLine(color = primaryColor, start = Offset(topCenter.x + rx, topCenter.y), end = Offset(botCenter.x + rx, botCenter.y), strokeWidth = strokeWidth)
                            // Top ellipse
                            drawOval(color = primaryColor, topLeft = Offset(topCenter.x - rx, topCenter.y - ry), size = Size(rx * 2, ry * 2), style = Stroke(width = strokeWidth))
                            // Bottom ellipse
                            drawOval(color = primaryColor, topLeft = Offset(botCenter.x - rx, botCenter.y - ry), size = Size(rx * 2, ry * 2), style = Stroke(width = strokeWidth))
                        }
                        else -> {
                            // Default geometric shape
                            drawRect(
                                color = primaryColor,
                                topLeft = Offset(size.width * 0.2f, size.height * 0.2f),
                                size = Size(size.width * 0.6f, size.height * 0.6f),
                                style = Stroke(width = strokeWidth)
                            )
                        }
                    }
                }
            }

            if (figure.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = figure.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun MarkdownTableRenderer(tableData: ContentBlock.Table) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                .horizontalScroll(scrollState)
                .padding(4.dp)
        ) {
            // Header Row
            if (tableData.headers.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .background(EduCiGreenPrimary.copy(alpha = 0.12f), RoundedCornerShape(6.dp))
                        .padding(vertical = 8.dp)
                ) {
                    for (header in tableData.headers) {
                        Text(
                            text = header,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = EduCiGreenDark,
                            modifier = Modifier
                                .widthIn(min = 110.dp, max = 220.dp)
                                .padding(horizontal = 10.dp)
                        )
                    }
                }
            }

            // Data Rows
            for ((index, row) in tableData.rows.withIndex()) {
                val rowBg = if (index % 2 == 0) Color.Transparent else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                Row(
                    modifier = Modifier
                        .background(rowBg)
                        .padding(vertical = 8.dp)
                ) {
                    for (cell in row) {
                        Box(
                            modifier = Modifier
                                .widthIn(min = 110.dp, max = 220.dp)
                                .padding(horizontal = 10.dp)
                        ) {
                            FormattedText(text = cell)
                        }
                    }
                }
                if (index < tableData.rows.size - 1) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                }
            }
        }
    }
}

sealed class ContentBlock {
    data class Heading1(val text: String) : ContentBlock()
    data class Heading2(val text: String) : ContentBlock()
    data class Heading3(val text: String) : ContentBlock()
    data class Paragraph(val text: String, val alignment: String = "left") : ContentBlock()
    data class BulletItem(val text: String, val indentLevel: Int = 0) : ContentBlock()
    data class NumberedItem(val number: String, val text: String, val indentLevel: Int = 0) : ContentBlock()
    data class Callout(val type: String, val text: String) : ContentBlock()
    data class MathDisplay(val formula: String) : ContentBlock()
    data class Table(val headers: List<String>, val rows: List<List<String>>) : ContentBlock()
    data class ImageBlock(val caption: String, val url: String, val alignment: String = "center", val size: String = "large") : ContentBlock()
    data class GeometricFigure(val type: String, val title: String, val description: String) : ContentBlock()
    data class Blockquote(val text: String) : ContentBlock()
    object Divider : ContentBlock()
}

fun parseContentBlocks(raw: String): List<ContentBlock> {
    val blocks = mutableListOf<ContentBlock>()
    val lines = raw.lines()
    var i = 0

    while (i < lines.size) {
        val line = lines[i].trim()

        if (line.isEmpty()) {
            i++
            continue
        }

        // Horizontal divider: --- or ***
        if (line == "---" || line == "***") {
            blocks.add(ContentBlock.Divider)
            i++
            continue
        }

        // Blockquote: > text
        if (line.startsWith("> ")) {
            val quoteLines = mutableListOf<String>()
            while (i < lines.size && lines[i].trim().startsWith("> ")) {
                quoteLines.add(lines[i].trim().removePrefix("> ").trim())
                i++
            }
            blocks.add(ContentBlock.Blockquote(quoteLines.joinToString("\n")))
            continue
        }

        // Geometric figure: :::figure:triangle ... :::
        if (line.startsWith(":::figure:")) {
            val figType = line.removePrefix(":::figure:").trim()
            val figLines = mutableListOf<String>()
            i++
            while (i < lines.size && !lines[i].trim().startsWith(":::")) {
                figLines.add(lines[i].trim())
                i++
            }
            if (i < lines.size && lines[i].trim().startsWith(":::")) {
                i++ // skip closing :::
            }
            val title = figLines.firstOrNull() ?: figType
            val desc = if (figLines.size > 1) figLines.drop(1).joinToString("\n") else ""
            blocks.add(ContentBlock.GeometricFigure(type = figType, title = title, description = desc))
            continue
        }

        // Pedagogical Callout Block: :::definition ... :::
        if (line.startsWith(":::")) {
            val type = line.removePrefix(":::").trim()
            val calloutLines = mutableListOf<String>()
            i++
            while (i < lines.size && !lines[i].trim().startsWith(":::")) {
                calloutLines.add(lines[i])
                i++
            }
            if (i < lines.size && lines[i].trim().startsWith(":::")) {
                i++ // skip closing :::
            }
            blocks.add(ContentBlock.Callout(type = type, text = calloutLines.joinToString("\n").trim()))
            continue
        }

        // Standalone Image: ![caption](url) or ![caption](url|center|large)
        val imageMatch = Regex("""^!\[(.*?)\]\((.*?)\)$""").find(line)
        if (imageMatch != null) {
            val caption = imageMatch.groupValues[1]
            val urlAndParams = imageMatch.groupValues[2].split("|")
            val url = urlAndParams.getOrNull(0)?.trim() ?: ""
            val alignment = urlAndParams.getOrNull(1)?.trim() ?: "center"
            val size = urlAndParams.getOrNull(2)?.trim() ?: "large"
            blocks.add(ContentBlock.ImageBlock(caption = caption, url = url, alignment = alignment, size = size))
            i++
            continue
        }

        // Standalone Math display: $$...$$
        if (line.startsWith("$$") && line.endsWith("$$") && line.length > 4) {
            val formula = line.removeSurrounding("$$").trim()
            blocks.add(ContentBlock.MathDisplay(formula))
            i++
            continue
        }

        // Markdown Table: starts with | and contains |
        if (line.startsWith("|") && line.endsWith("|")) {
            val tableLines = mutableListOf<String>()
            while (i < lines.size && lines[i].trim().startsWith("|") && lines[i].trim().endsWith("|")) {
                tableLines.add(lines[i].trim())
                i++
            }
            if (tableLines.size >= 2) {
                val headerLine = tableLines[0]
                val headers = headerLine.split("|")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }

                val rows = mutableListOf<List<String>>()
                val startIndex = if (tableLines[1].contains("---")) 2 else 1
                for (r in startIndex until tableLines.size) {
                    val cells = tableLines[r].split("|")
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }
                    if (cells.isNotEmpty()) {
                        rows.add(cells)
                    }
                }
                blocks.add(ContentBlock.Table(headers, rows))
            }
            continue
        }

        // Headings
        if (line.startsWith("# ")) {
            blocks.add(ContentBlock.Heading1(line.removePrefix("# ").trim()))
            i++
            continue
        }
        if (line.startsWith("## ")) {
            blocks.add(ContentBlock.Heading2(line.removePrefix("## ").trim()))
            i++
            continue
        }
        if (line.startsWith("### ") || line.startsWith("#### ")) {
            blocks.add(ContentBlock.Heading3(line.removePrefix("### ").removePrefix("#### ").trim()))
            i++
            continue
        }

        // Bulleted lists (check indentation)
        val rawLine = lines[i]
        val indentSpaces = rawLine.takeWhile { it == ' ' }.length
        val indentLevel = indentSpaces / 2

        if (line.startsWith("- ") || line.startsWith("* ")) {
            blocks.add(ContentBlock.BulletItem(line.substring(2).trim(), indentLevel))
            i++
            continue
        }

        // Numbered lists
        val numberedMatch = Regex("""^(\d+)\.\s+(.*)""").find(line)
        if (numberedMatch != null) {
            val num = numberedMatch.groupValues[1]
            val rest = numberedMatch.groupValues[2]
            blocks.add(ContentBlock.NumberedItem(num, rest, indentLevel))
            i++
            continue
        }

        // Normal paragraph (check if aligned via <p align="center"> or plain)
        if (line.startsWith("<p align=\"center\">") && line.endsWith("</p>")) {
            val text = line.removePrefix("<p align=\"center\">").removeSuffix("</p>").trim()
            blocks.add(ContentBlock.Paragraph(text, alignment = "center"))
            i++
            continue
        }
        if (line.startsWith("<p align=\"right\">") && line.endsWith("</p>")) {
            val text = line.removePrefix("<p align=\"right\">").removeSuffix("</p>").trim()
            blocks.add(ContentBlock.Paragraph(text, alignment = "right"))
            i++
            continue
        }

        blocks.add(ContentBlock.Paragraph(line))
        i++
    }

    return blocks
}
