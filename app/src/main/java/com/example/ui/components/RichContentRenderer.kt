package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                is ContentBlock.BulletItem -> {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "• ",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        )
                        FormattedText(text = block.text)
                    }
                }
                is ContentBlock.NumberedItem -> {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
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
                    FormattedText(text = block.text)
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
fun FormattedText(text: String, modifier: Modifier = Modifier) {
    val annotated = buildAnnotatedString {
        var currentIndex = 0
        val regex = Regex("""(\*\*([^*]+)\*\*)|(\*([^*]+)\*)|(__([^_]+)__)|(\$\$([^$]+)\$\$)|(\$([^$]+)\$)""")
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
                // Display Math inline: $$formula$$
                match.groupValues[7].isNotEmpty() -> {
                    withStyle(
                        SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            color = EduCiGreenDark,
                            background = EduCiGreenContainer.copy(alpha = 0.5f)
                        )
                    ) {
                        append(" ${match.groupValues[8]} ")
                    }
                }
                // Inline Math: $formula$
                match.groupValues[9].isNotEmpty() -> {
                    withStyle(
                        SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Medium,
                            color = EduCiGreenDark,
                            background = EduCiGreenContainer.copy(alpha = 0.4f)
                        )
                    ) {
                        append(" ${match.groupValues[10]} ")
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
            color = MaterialTheme.colorScheme.onSurface
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
            icon = Icons.Default.MenuBook,
            title = "DÉFINITION",
            titleColor = EduCiGreenPrimary
        )
        "exemple" -> CalloutConfig(
            bgColor = Color(0xFFEFF6FF),
            borderColor = Color(0xFF60A5FA),
            icon = Icons.Default.Lightbulb,
            title = "EXEMPLE CONCRET",
            titleColor = Color(0xFF1D4ED8)
        )
        "attention" -> CalloutConfig(
            bgColor = Color(0xFFFEF2F2),
            borderColor = Color(0xFFF87171),
            icon = Icons.Default.Warning,
            title = "ATTENTION / PIÈGE FRÉQUENT",
            titleColor = Color(0xFFB91C1C)
        )
        "conseil" -> CalloutConfig(
            bgColor = Color(0xFFFFFBEB),
            borderColor = Color(0xFFFBBF24),
            icon = Icons.Default.TipsAndUpdates,
            title = "CONSEIL DU PROFESSEUR",
            titleColor = Color(0xFFB45309)
        )
        else -> CalloutConfig(
            bgColor = MaterialTheme.colorScheme.surfaceVariant,
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
fun MathDisplayCard(formula: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = EduCiGreenContainer.copy(alpha = 0.6f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = formula,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = EduCiGreenDark
            )
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
                        .background(EduCiGreenPrimary.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
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
    data class Paragraph(val text: String) : ContentBlock()
    data class BulletItem(val text: String) : ContentBlock()
    data class NumberedItem(val number: String, val text: String) : ContentBlock()
    data class Callout(val type: String, val text: String) : ContentBlock()
    data class MathDisplay(val formula: String) : ContentBlock()
    data class Table(val headers: List<String>, val rows: List<List<String>>) : ContentBlock()
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

        // Horizontal divider: ---
        if (line == "---" || line == "***") {
            blocks.add(ContentBlock.Divider)
            i++
            continue
        }

        // Callout Block: :::definition ... :::
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
                // Skip index 1 if it's separator line | --- | --- |
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

        // Bulleted lists
        if (line.startsWith("- ") || line.startsWith("* ")) {
            blocks.add(ContentBlock.BulletItem(line.substring(2).trim()))
            i++
            continue
        }

        // Numbered lists
        val numberedMatch = Regex("""^(\d+)\.\s+(.*)""").find(line)
        if (numberedMatch != null) {
            val num = numberedMatch.groupValues[1]
            val rest = numberedMatch.groupValues[2]
            blocks.add(ContentBlock.NumberedItem(num, rest))
            i++
            continue
        }

        // Normal paragraph
        blocks.add(ContentBlock.Paragraph(line))
        i++
    }

    return blocks
}
