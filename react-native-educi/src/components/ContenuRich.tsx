import React from 'react';
import { View, Text, StyleSheet, ScrollView } from 'react-native';
import { useAppTheme } from '../context/ThemeContext';
import { RichBlock, NOTE_META } from '../data/richBlocks';
import { ThemeColors } from '../theme/colors';

/**
 * ContenuRich — rendu adaptatif des blocs pédagogiques riches.
 * Chaque type de bloc est affiché avec une mise en forme qui lui est propre
 * (encadrés colorés, tableaux, graphiques SVG, formules en monospace, etc.)
 * et qui s'adapte automatiquement au thème clair/sombre.
 */
export const ContenuRich: React.FC<{ blocks: RichBlock[] }> = ({ blocks }) => {
  const { colors } = useAppTheme();

  if (!blocks || blocks.length === 0) {
    return null;
  }

  return (
    <View>
      {blocks.map((block, index) => (
        <BlockRenderer key={index} block={block} colors={colors} />
      ))}
    </View>
  );
};

function BlockRenderer({ block, colors }: { block: RichBlock; colors: ThemeColors }) {
  switch (block.type) {
    case 'heading':
      return (
        <Text
          style={[
            block.level === 1
              ? styles.h1
              : block.level === 2
              ? styles.h2
              : styles.h3,
            { color: colors.textPrimary },
            block.bold && { fontWeight: '800' },
          ]}
        >
          {block.text}
        </Text>
      );

    case 'paragraph':
      return (
        <Text
          style={[
            styles.paragraph,
            { color: colors.textPrimary },
            block.bold && styles.bold,
            block.italic && styles.italic,
          ]}
        >
          {block.text}
        </Text>
      );

    case 'list':
      return (
        <View style={styles.listBlock}>
          {block.items.map((item, i) => (
            <View key={i} style={styles.listRow}>
              <Text style={[styles.listBullet, { color: colors.primary }]}>
                {block.ordered ? `${i + 1}.` : '•'}
              </Text>
              <Text style={[styles.listText, { color: colors.textPrimary }]}>{item}</Text>
            </View>
          ))}
        </View>
      );

    case 'table':
      return <RichTable block={block} colors={colors} />;

    case 'formula':
      return (
        <View style={[styles.formulaBox, { backgroundColor: colors.surfaceVariant, borderColor: colors.primary }]}>
          <Text style={[styles.formulaText, { color: colors.textPrimary }]}>{block.text}</Text>
        </View>
      );

    case 'note':
      return <RichNote block={block} colors={colors} />;

    case 'figure':
      return (
        <View style={[styles.figureBox, { backgroundColor: colors.surfaceVariant, borderColor: colors.border }]}>
          <View style={[styles.figureCanvas, { backgroundColor: colors.surface }]}>
            <Text style={styles.figureEmoji}>{block.emoji}</Text>
            <Text style={[styles.figureLabel, { color: colors.textSecondary }]}>{block.label}</Text>
          </View>
          {block.caption ? (
            <Text style={[styles.figureCaption, { color: colors.textMuted }]}>Fig. {block.caption}</Text>
          ) : null}
        </View>
      );

    case 'graph':
      return <RichGraph block={block} colors={colors} />;

    default:
      return null;
  }
}

/* ---- Tableau ---- */
const RichTable: React.FC<{ block: Extract<RichBlock, { type: 'table' }>; colors: ThemeColors }> = ({ block, colors }) => {
  return (
    <View style={[styles.tableWrap, { borderColor: colors.border }]}>
      {block.headers.length > 0 && (
        <View style={[styles.tableRow, { backgroundColor: colors.badgeBg }]}>
          {block.headers.map((h, i) => (
            <Text key={i} style={[styles.tableCell, styles.tableHeaderText, { color: colors.primary }]}>
              {h}
            </Text>
          ))}
        </View>
      )}
      {block.rows.map((row, r) => (
        <View key={r} style={[styles.tableRow, r % 2 ? { backgroundColor: colors.surfaceVariant } : { backgroundColor: colors.surface }]}>
          {row.map((cell, c) => (
            <Text key={c} style={[styles.tableCell, { color: colors.textPrimary, flex: block.headers.length || 1 }]}>
              {cell}
            </Text>
          ))}
        </View>
      ))}
    </View>
  );
};

/* ---- Encadré pédagogique ---- */
const RichNote: React.FC<{ block: Extract<RichBlock, { type: 'note' }>; colors: ThemeColors }> = ({ block, colors }) => {
  const meta = NOTE_META[block.variant];
  const accent = colors.accent;
  return (
    <View style={[styles.noteBox, { backgroundColor: colors.badgeBg, borderColor: colors.primary, borderLeftColor: accent }]}>
      <Text style={[styles.noteTitle, { color: colors.primary }]}>
        {meta.icon} {meta.title}
      </Text>
      <Text style={[styles.noteText, { color: colors.textPrimary }]}>{block.text}</Text>
    </View>
  );
};

/* ---- Graphique simple (barres / lignes) en SVG maison ---- */
const RichGraph: React.FC<{ block: Extract<RichBlock, { type: 'graph' }>; colors: ThemeColors }> = ({ block, colors }) => {
  const maxVal = Math.max(...block.values, 1);
  const chartWidth = 280;
  const chartHeight = 120;

  return (
    <View style={[styles.graphBox, { backgroundColor: colors.surfaceVariant, borderColor: colors.border }]}>
      <Text style={[styles.graphTitle, { color: colors.textPrimary }]}>{block.title}</Text>
      <View style={styles.graphArea}>
        {block.kind === 'bar' ? (
          <View style={styles.barChart}>
            {block.values.map((v, i) => {
              const barH = Math.max((v / maxVal) * chartHeight, 4);
              return (
                <View key={i} style={styles.barCol}>
                  <Text style={[styles.barValue, { color: colors.textSecondary }]}>{v}</Text>
                  <View style={[styles.bar, { height: barH, backgroundColor: i % 2 ? colors.accent : colors.primary }]} />
                  <Text style={[styles.barLabel, { color: colors.textMuted }]} numberOfLines={1}>
                    {block.labels[i] ?? ''}
                  </Text>
                </View>
              );
            })}
          </View>
        ) : (
          // Graphique en lignes (points + segments)
          <View style={{ alignItems: 'center' }}>
            <View style={{ flexDirection: 'row', alignItems: 'flex-end', height: chartHeight + 20 }}>
              {block.values.map((v, i) => {
                const h = Math.max((v / maxVal) * chartHeight, 4);
                const isLast = i === block.values.length - 1;
                return (
                  <View key={i} style={{ alignItems: 'center' }}>
                    <View style={[styles.linePoint, { backgroundColor: colors.primary }]} />
                    <Text style={[styles.lineValue, { color: colors.textSecondary }]}>{v}</Text>
                    {!isLast && <View style={[styles.lineSegment, { height: h, backgroundColor: i % 2 ? colors.accent : colors.primary }]} />}
                  </View>
                );
              })}
            </View>
            <Text style={[styles.lineSeriesLabel, { color: colors.textMuted }]}>{block.labels.join(' • ')}</Text>
          </View>
        )}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  h1: { fontSize: 20, fontWeight: '800', marginTop: 14, marginBottom: 8 },
  h2: { fontSize: 17, fontWeight: '700', marginTop: 12, marginBottom: 6 },
  h3: { fontSize: 15, fontWeight: '700', marginTop: 10, marginBottom: 5 },
  paragraph: { fontSize: 15, lineHeight: 24, marginBottom: 14 },
  bold: { fontWeight: '800' },
  italic: { fontStyle: 'italic' },
  listBlock: { marginBottom: 14, gap: 8 },
  listRow: { flexDirection: 'row', alignItems: 'flex-start', gap: 8 },
  listBullet: { fontSize: 15, fontWeight: '700', width: 18 },
  listText: { fontSize: 15, lineHeight: 22, flex: 1 },
  formulaBox: { padding: 14, borderRadius: 10, borderWidth: 1, marginVertical: 8, alignItems: 'center' },
  formulaText: { fontSize: 16, fontWeight: '700', fontStyle: 'italic' },
  noteBox: { padding: 14, borderRadius: 10, borderWidth: 1, borderLeftWidth: 5, marginVertical: 10 },
  noteTitle: { fontSize: 13, fontWeight: '800', marginBottom: 6 },
  noteText: { fontSize: 14, lineHeight: 21 },
  tableWrap: { borderWidth: 1, borderRadius: 10, overflow: 'hidden', marginVertical: 10 },
  tableRow: { flexDirection: 'row' },
  tableCell: { fontSize: 13, padding: 10, flex: 1, textAlign: 'center' },
  tableHeaderText: { fontWeight: '800' },
  figureBox: { borderWidth: 1, borderRadius: 12, padding: 12, marginVertical: 10 },
  figureCanvas: { borderRadius: 8, height: 120, alignItems: 'center', justifyContent: 'center', borderWidth: 1, borderColor: 'rgba(0,0,0,0.08)' },
  figureEmoji: { fontSize: 44, marginBottom: 6 },
  figureLabel: { fontSize: 13, fontWeight: '600' },
  figureCaption: { fontSize: 11, marginTop: 8, textAlign: 'center', fontStyle: 'italic' },
  graphBox: { borderRadius: 12, padding: 14, marginVertical: 10, borderWidth: 1 },
  graphTitle: { fontSize: 14, fontWeight: '800', marginBottom: 12, textAlign: 'center' },
  graphArea: { alignItems: 'center' },
  barChart: { flexDirection: 'row', alignItems: 'flex-end', gap: 10 },
  barCol: { alignItems: 'center', width: 44 },
  barValue: { fontSize: 11, marginBottom: 4 },
  bar: { width: 26, borderRadius: 4 },
  barLabel: { fontSize: 10, marginTop: 6, width: 44, textAlign: 'center' },
  linePoint: { width: 10, height: 10, borderRadius: 5 },
  lineValue: { fontSize: 11, marginVertical: 2 },
  lineSegment: { width: 6, minHeight: 4, borderRadius: 3, marginVertical: 4 },
  lineSeriesLabel: { fontSize: 11, marginTop: 8 },
});