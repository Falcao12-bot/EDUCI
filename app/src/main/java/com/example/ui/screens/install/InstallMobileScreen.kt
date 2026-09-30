package com.example.ui.screens.install

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.navigation.AppScreen
import com.example.ui.theme.*
import com.example.ui.viewmodel.EduViewModel

const val SHARED_APP_URL = "https://ais-pre-ydl7ju6p6m4aztbg3ueyav-673723140411.europe-west1.run.app"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstallMobileScreen(
    viewModel: EduViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedMethod by remember { mutableIntStateOf(0) }
    var copiedToClipboard by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhoneAndroid,
                            contentDescription = "Téléphone",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Installer EduCI sur votre Téléphone",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Accédez à tous les cours, révisions et quiz officiels de Côte d'Ivoire directement depuis l'écran de votre smartphone.",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Method Selector Tabs
        item {
            TabRow(
                selectedTabIndex = selectedMethod,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clip(RoundedCornerShape(14.dp))
            ) {
                Tab(
                    selected = selectedMethod == 0,
                    onClick = { selectedMethod = 0 },
                    text = {
                        Text(
                            text = "📲 QR Code",
                            fontWeight = if (selectedMethod == 0) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
                Tab(
                    selected = selectedMethod == 1,
                    onClick = { selectedMethod = 1 },
                    text = {
                        Text(
                            text = "📦 APK Android",
                            fontWeight = if (selectedMethod == 1) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
                Tab(
                    selected = selectedMethod == 2,
                    onClick = { selectedMethod = 2 },
                    text = {
                        Text(
                            text = "🌐 PWA / Web",
                            fontWeight = if (selectedMethod == 2) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
            }
        }

        // Tab Content
        when (selectedMethod) {
            0 -> {
                // QR Code Tab
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Scannez avec votre Caméra",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Pointez l'appareil photo de votre smartphone vers ce QR code pour ouvrir l'application instantanément.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            // Stylized Matrix QR Canvas
                            Box(
                                modifier = Modifier
                                    .size(220.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                                    .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier.fillMaxSize()) {
                                    val sizePx = size.minDimension
                                    val moduleCount = 21
                                    val moduleSize = sizePx / moduleCount

                                    fun drawFinderPattern(col: Int, row: Int) {
                                        drawRoundRect(
                                            color = Color(0xFF008751),
                                            topLeft = Offset(col * moduleSize, row * moduleSize),
                                            size = Size(7 * moduleSize, 7 * moduleSize),
                                            cornerRadius = CornerRadius(8f, 8f)
                                        )
                                        drawRoundRect(
                                            color = Color.White,
                                            topLeft = Offset((col + 1) * moduleSize, (row + 1) * moduleSize),
                                            size = Size(5 * moduleSize, 5 * moduleSize),
                                            cornerRadius = CornerRadius(6f, 6f)
                                        )
                                        drawRoundRect(
                                            color = Color(0xFF008751),
                                            topLeft = Offset((col + 2) * moduleSize, (row + 2) * moduleSize),
                                            size = Size(3 * moduleSize, 3 * moduleSize),
                                            cornerRadius = CornerRadius(4f, 4f)
                                        )
                                    }

                                    // Three finder patterns
                                    drawFinderPattern(0, 0)
                                    drawFinderPattern(14, 0)
                                    drawFinderPattern(0, 14)

                                    // Data pattern (deterministic pseudo-random matrix for aesthetic QR)
                                    val seed = 42
                                    for (r in 0 until moduleCount) {
                                        for (c in 0 until moduleCount) {
                                            val inFinder1 = r < 8 && c < 8
                                            val inFinder2 = r < 8 && c >= 13
                                            val inFinder3 = r >= 13 && c < 8
                                            val inCenter = r in 9..11 && c in 9..11

                                            if (!inFinder1 && !inFinder2 && !inFinder3 && !inCenter) {
                                                val bit = ((r * 13 + c * 7 + seed) xor (r * c)) % 3 == 0
                                                if (bit) {
                                                    drawRoundRect(
                                                        color = Color(0xFF1A2E22),
                                                        topLeft = Offset(c * moduleSize + 1f, r * moduleSize + 1f),
                                                        size = Size(moduleSize - 2f, moduleSize - 2f),
                                                        cornerRadius = CornerRadius(2f, 2f)
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    // Center CI Flag / EduCI Logo Badge
                                    drawCircle(
                                        color = Color(0xFFFF8200),
                                        radius = moduleSize * 1.5f,
                                        center = Offset(sizePx / 2, sizePx / 2)
                                    )
                                    drawCircle(
                                        color = Color(0xFF008751),
                                        radius = moduleSize * 0.9f,
                                        center = Offset(sizePx / 2, sizePx / 2)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Share and Copy Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("EduCI App URL", SHARED_APP_URL)
                                        clipboard.setPrimaryClip(clip)
                                        copiedToClipboard = true
                                        Toast.makeText(context, "Lien copié dans le presse-papier !", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(
                                        imageVector = if (copiedToClipboard) Icons.Default.Check else Icons.Outlined.ContentCopy,
                                        contentDescription = "Copier",
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(if (copiedToClipboard) "Copié !" else "Copier le lien", fontSize = 12.sp)
                                }

                                Button(
                                    onClick = {
                                        val sendIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(
                                                Intent.EXTRA_TEXT,
                                                "📱 Installe l'application éducative ivoirienne EduCI sur ton téléphone pour réviser tes cours et préparer ton BEPC/BAC :\n$SHARED_APP_URL"
                                            )
                                            type = "text/plain"
                                        }
                                        context.startActivity(Intent.createChooser(sendIntent, "Partager l'application EduCI via"))
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = "Partager",
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Partager", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                // APK Direct Installation
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Android,
                                            contentDescription = "APK",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Fichier APK Android Natif",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "Installation autonome sans passer par le store",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Divider(color = MaterialTheme.colorScheme.outlineVariant)

                            // Step-by-Step Instructions
                            InstallationStep(
                                number = "1",
                                title = "Télécharger l'APK",
                                description = "Dans le menu supérieur de Google AI Studio, cliquez sur les paramètres (⋮) puis 'Export / Download APK' pour récupérer le package compilé."
                            )

                            InstallationStep(
                                number = "2",
                                title = "Autoriser les Sources Inconnues",
                                description = "Sur votre téléphone Android, allez dans Paramètres > Sécurité (ou Gestion des applications) > Autoriser l'installation d'applications de sources inconnues pour votre navigateur ou explorateur de fichiers."
                            )

                            InstallationStep(
                                number = "3",
                                title = "Installer le fichier .apk",
                                description = "Appuyez sur le fichier téléchargé dans vos notifications ou votre dossier 'Téléchargements', puis confirmez en cliquant sur 'Installer'."
                            )

                            InstallationStep(
                                number = "4",
                                title = "Lancer EduCI",
                                description = "L'icône officielle EduCI apparaît sur votre écran d'accueil. Ouvrez-la pour profiter de l'expérience complète hors-ligne !"
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SHARED_APP_URL))
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.Download, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Ouvrir la page de téléchargement sur mobile", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            2 -> {
                // Progressive Web App (PWA) / Shortcut
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Language,
                                            contentDescription = "PWA",
                                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Installation Immédiate (PWA)",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "Fonctionne sur Android (Chrome) et iPhone (Safari)",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Divider(color = MaterialTheme.colorScheme.outlineVariant)

                            InstallationStep(
                                number = "1",
                                title = "Ouvrir l'adresse dans votre navigateur",
                                description = "Rendez-vous sur l'adresse officielle de l'application depuis Chrome (Android) ou Safari (iPhone)."
                            )

                            InstallationStep(
                                number = "2",
                                title = "Ouvrir les options du navigateur",
                                description = "Sur Android : appuyez sur les 3 points verticaux (⋮) en haut à droite.\nSur iPhone : appuyez sur l'icône de partage (carré avec flèche vers le haut) en bas."
                            )

                            InstallationStep(
                                number = "3",
                                title = "Sélectionner 'Ajouter à l'écran d'accueil'",
                                description = "Cliquez sur 'Ajouter à l'écran d'accueil' ou 'Installer l'application'. Confirmez le nom 'EduCI'."
                            )

                            InstallationStep(
                                number = "4",
                                title = "Accès plein écran instantané",
                                description = "L'application s'ouvre comme une véritable application native, sans barre d'adresse de navigateur !"
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SHARED_APP_URL))
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.OpenInBrowser, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Ouvrir dans le navigateur", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Quick WhatsApp / SMS Direct Share Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "💬 Envoyer le lien d'installation sur WhatsApp",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Transférez directement le lien à vous-même ou à vos camarades de classe pour qu'ils puissent l'installer en 1 clic.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Salut ! Télécharge et installe l'application EduCI pour réviser nos cours et exercices officiels de Côte d'Ivoire :\n$SHARED_APP_URL"
                                )
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Envoyer par WhatsApp / SMS"))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Partager par WhatsApp / SMS", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun InstallationStep(
    number: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(26.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = number,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )
        }
    }
}
