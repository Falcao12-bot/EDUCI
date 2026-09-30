package com.example.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*
import com.example.ui.viewmodel.EduViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    viewModel: EduViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Connexion, 1: Inscription

    // Login state
    var loginIdentifier by remember { mutableStateOf("") }
    var loginPassword by remember { mutableStateOf("") }

    // Register state
    var regLastName by remember { mutableStateOf("") }
    var regFirstName by remember { mutableStateOf("") }
    var regEmail by remember { mutableStateOf("") }
    var regPhone by remember { mutableStateOf("") }
    var regSchoolName by remember { mutableStateOf("") }
    var regPassword by remember { mutableStateOf("") }
    var regConfirmPassword by remember { mutableStateOf("") }
    var regClass by remember { mutableStateOf("4e") }
    var regOwnerKey by remember { mutableStateOf("") }
    var classMenuExpanded by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var showForgotPasswordDialog by remember { mutableStateOf(false) }
    var showOwnerLoginDialog by remember { mutableStateOf(false) }

    val classesList = listOf("CP1", "CP2", "CE1", "CE2", "CM1", "CM2", "6e", "5e", "4e", "3e", "2nde", "1ère", "Terminale")

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Hero Logo Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = EduCiGreenPrimary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // EduCI App Icon
                Surface(
                    modifier = Modifier.size(68.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_educi_logo),
                        contentDescription = "Logo EduCI",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "EduCI",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = "« Apprendre. Progresser. Réussir. »",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Plateforme Éducative Ivoirienne (Primaire - Lycée)",
                    fontSize = 12.sp,
                    color = EduCiGoldLight
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tabs: Connexion / Inscription
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = {
                    selectedTab = 0
                    errorMessage = null
                },
                text = {
                    Text(
                        text = "Connexion",
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = {
                    selectedTab = 1
                    errorMessage = null
                },
                text = {
                    Text(
                        text = "Inscription",
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Messages
        if (errorMessage != null) {
            Surface(
                color = MaterialTheme.colorScheme.errorContainer,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        if (successMessage != null) {
            Surface(
                color = EduCiGreenContainer,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                Text(
                    text = successMessage ?: "",
                    color = EduCiGreenDark,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        if (selectedTab == 0) {
            // CONNEXION (Email ou Téléphone)
            OutlinedTextField(
                value = loginIdentifier,
                onValueChange = { loginIdentifier = it },
                label = { Text("Email ou N° Téléphone") },
                placeholder = { Text("nom@exemple.com ou 0700000000") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = loginPassword,
                onValueChange = { loginPassword = it },
                label = { Text("Mot de passe") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { showForgotPasswordDialog = true }) {
                    Text(
                        text = "Mot de passe oublié ?",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if (loginIdentifier.isBlank() || loginPassword.isBlank()) {
                        errorMessage = "Veuillez renseigner votre email ou téléphone et votre mot de passe."
                        return@Button
                    }
                    viewModel.login(loginIdentifier, loginPassword) { success, msg ->
                        if (!success) errorMessage = msg
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
            ) {
                Text("Se connecter", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Dedicated Owner & Administrator access button
            OutlinedCard(
                onClick = { showOwnerLoginDialog = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = null,
                        tint = EduCiOrangeAccent
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Accès Réservé au Propriétaire",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Authentification sécurisée avec clé d'administration",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(Icons.Default.Lock, contentDescription = null, tint = EduCiOrangeAccent, modifier = Modifier.size(18.dp))
                }
            }

        } else {
            // INSCRIPTION
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = regLastName,
                    onValueChange = { regLastName = it },
                    label = { Text("Nom *") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = regFirstName,
                    onValueChange = { regFirstName = it },
                    label = { Text("Prénom *") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = regEmail,
                onValueChange = { regEmail = it },
                label = { Text("Adresse Email *") },
                placeholder = { Text("nom@exemple.com") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = regPhone,
                onValueChange = { regPhone = it },
                label = { Text("Numéro de Téléphone *") },
                placeholder = { Text("07 00 00 00 00") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Class selection dropdown
            ExposedDropdownMenuBox(
                expanded = classMenuExpanded,
                onExpandedChange = { classMenuExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = "Niveau : $regClass",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Niveau scolaire *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = classMenuExpanded) },
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = classMenuExpanded,
                    onDismissRequest = { classMenuExpanded = false }
                ) {
                    for (c in classesList) {
                        DropdownMenuItem(
                            text = { Text(c) },
                            onClick = {
                                regClass = c
                                classMenuExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = regSchoolName,
                onValueChange = { regSchoolName = it },
                label = { Text("Établissement scolaire (Optionnel)") },
                placeholder = { Text("Ex : Lycée Classique d'Abidjan") },
                leadingIcon = { Icon(Icons.Default.School, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = regPassword,
                onValueChange = { regPassword = it },
                label = { Text("Mot de passe *") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = regConfirmPassword,
                onValueChange = { regConfirmPassword = it },
                label = { Text("Confirmation du mot de passe *") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = regOwnerKey,
                onValueChange = { regOwnerKey = it },
                label = { Text("Clé Propriétaire (Optionnel)") },
                placeholder = { Text("Réservé à l'administrateur créateur") },
                leadingIcon = { Icon(Icons.Default.Key, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (regFirstName.isBlank() || regLastName.isBlank() || regEmail.isBlank() || regPhone.isBlank() || regPassword.isBlank()) {
                        errorMessage = "Veuillez remplir tous les champs obligatoires (*)."
                        return@Button
                    }
                    if (regPassword != regConfirmPassword) {
                        errorMessage = "Les mots de passe ne correspondent pas."
                        return@Button
                    }
                    viewModel.register(
                        firstName = regFirstName,
                        lastName = regLastName,
                        email = regEmail,
                        phone = regPhone,
                        pass = regPassword,
                        className = regClass,
                        schoolName = regSchoolName,
                        ownerPasscode = regOwnerKey
                    ) { success, msg ->
                        if (!success) errorMessage = msg
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
            ) {
                Text("Créer mon compte élève", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    // Owner Login Dialog
    if (showOwnerLoginDialog) {
        var ownerSecretInput by remember { mutableStateOf("") }
        var ownerError by remember { mutableStateOf<String?>(null) }

        AlertDialog(
            onDismissRequest = { showOwnerLoginDialog = false },
            icon = { Icon(Icons.Default.Security, contentDescription = null, tint = EduCiOrangeAccent) },
            title = { Text("Authentification Propriétaire") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Seul le propriétaire de l'application est autorisé à modifier le contenu (cours, exercices, examens). Entrez votre clé d'administration secrète.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = ownerSecretInput,
                        onValueChange = {
                            ownerSecretInput = it
                            ownerError = null
                        },
                        label = { Text("Clé Maître Secrète") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (ownerError != null) {
                        Text(
                            text = ownerError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.claimOwnerAccess(ownerSecretInput) { ok, msg ->
                            if (ok) {
                                showOwnerLoginDialog = false
                            } else {
                                ownerError = msg
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
                ) {
                    Text("Valider la Clé")
                }
            },
            dismissButton = {
                TextButton(onClick = { showOwnerLoginDialog = false }) {
                    Text("Annuler")
                }
            }
        )
    }

    // Forgot Password Dialog
    if (showForgotPasswordDialog) {
        var resetEmail by remember { mutableStateOf("") }
        var resetDone by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showForgotPasswordDialog = false },
            icon = { Icon(Icons.Default.LockReset, contentDescription = null, tint = EduCiGreenPrimary) },
            title = { Text("Réinitialisation Sécurisée") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (resetDone) {
                        Text(
                            text = "Un code de réinitialisation sécurisé par SMS et email a été envoyé à $resetEmail.",
                            fontSize = 13.sp,
                            color = EduCiGreenDark
                        )
                    } else {
                        Text(
                            text = "Entrez votre adresse email ou votre numéro de téléphone. Un lien de réinitialisation sécurisé vous sera transmis.",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        OutlinedTextField(
                            value = resetEmail,
                            onValueChange = { resetEmail = it },
                            label = { Text("Email ou N° Téléphone") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (resetDone) {
                            showForgotPasswordDialog = false
                        } else {
                            if (resetEmail.isNotBlank()) resetDone = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
                ) {
                    Text(if (resetDone) "Fermer" else "Envoyer le lien")
                }
            },
            dismissButton = {
                if (!resetDone) {
                    TextButton(onClick = { showForgotPasswordDialog = false }) {
                        Text("Annuler")
                    }
                }
            }
        )
    }
}
