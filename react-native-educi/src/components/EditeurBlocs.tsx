import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  StyleSheet,
  TouchableOpacity,
  ScrollView,
} from 'react-native';
import { useAppTheme } from '../context/ThemeContext';
import { RichBlock, NOTE_META } from '../data/richBlocks';

/**
 * EditeurBlocs — éditeur riche en saisie pédagogique pour l'administrateur.
 * Permet d'ajouter et de modifier des blocs : titres, paragraphes (gras/italique),
 * listes, tableaux, figures, graphiques, formules et encadrés pédagogiques.
 * Tourne hors-ligne (aucune sauvegarde cloud) : le contenu est gardé en état React.
 */
interface Props {
  initialBlocks: RichBlock[];
  onChange: (blocks: RichBlock[]) => void;
}

export const EditeurBlocs: React.FC<Props> = ({ initialBlocks, onChange }) => {
  const { colors } = useAppTheme();
  const [blocks, setBlocks] = useState<RichBlock[]>(initialBlocks);

  const commit = (next: RichBlock[]) => {
    setBlocks(next);
    onChange(next);
  };

  const append = (block: RichBlock) => commit([...blocks, block]);

  const updateAt = (index: number, patch: Partial<RichBlock>) => {
    const next = blocks.map((b, i) => (i === index ? ({ ...b, ...patch } as RichBlock) : b));
    commit(next);
  };

  const removeAt = (index: number) => {
    commit(blocks.filter((_, i) => i !== index));
  };

  const moveAt = (index: number, dir: -1 | 1) => {
    const target = index + dir;
    if (target < 0 || target >= blocks.length) return;
    const next = [...blocks];
    [next[index], next[target]] = [next[target], next[index]];
    commit(next);
  };

  const addBlock = (type: RichBlock['type']) => {
    const base: RichBlock = {
      type: 'paragraph',
      text: '',
    };
    if (type === 'heading') {
      append({ type: 'heading', text: '', level: 2 });
    } else if (type === 'list') {
      append({ type: 'list', items: ['Point 1', 'Point 2'] });
    } else if (type === 'table') {
      append({ type: 'table', headers: ['Colonne A', 'Colonne B'], rows: [['', '']] });
    } else if (type === 'figure') {
      append({ type: 'figure', emoji: '📐', label: 'Figure 1', caption: 'Figure illustrative' });
    } else if (type === 'graph') {
      append({ type: 'graph', title: 'Graphique', kind: 'bar', labels: ['A', 'B', 'C'], values: [30, 60, 45] });
    } else if (type === 'formula') {
      append({ type: 'formula', text: 'a² + b² = c²' });
    } else if (type === 'note') {
      append({ type: 'note', variant: 'definition', text: '' });
    } else {
      commit([...blocks, base]);
    }
  };

  const toggleNoteVariant = (index: number) => {
    const b = blocks[index];
    if (b.type !== 'note') return;
    const variants = Object.keys(NOTE_META) as (keyof typeof NOTE_META)[];
    const cur = variants.indexOf(b.variant);
    const nextV = variants[(cur + 1) % variants.length];
    updateAt(index, { variant: nextV });
  };

  const toggleListOrder = (index: number) => {
    const b = blocks[index];
    if (b.type !== 'list') return;
    updateAt(index, { ordered: !b.ordered });
  };

  const toolbar: { key: string; label: string; action: () => void }[] = [
    { key: 'heading', label: 'H1', action: () => addBlock('heading') },
    { key: 'p', label: 'Texte', action: () => addBlock('paragraph') },
    { key: 'list', label: '☰ Liste', action: () => addBlock('list') },
    { key: 'table', label: '▦ Tableau', action: () => addBlock('table') },
    { key: 'figure', label: '🖼 Figure', action: () => addBlock('figure') },
    { key: 'graph', label: '📊 Graphique', action: () => addBlock('graph') },
    { key: 'formula', label: 'ƒ Formule', action: () => addBlock('formula') },
    { key: 'note', label: '📌 Encadré', action: () => addBlock('note') },
  ];

  const picker: { key: string; label: string; onPress: () => void }[] = [
    { key: 'h1', label: 'Titre H1', onPress: () => append({ type: 'heading', text: '', level: 1 }) },
    { key: 'h2', label: 'Titre H2', onPress: () => append({ type: 'heading', text: '', level: 2 }) },
    { key: 'h3', label: 'Titre H3', onPress: () => append({ type: 'heading', text: '', level: 3 }) },
    { key: 'p', label: 'Paragraphe', onPress: () => append({ type: 'paragraph', text: '' }) },
    { key: 'list', label: 'Liste', onPress: () => append({ type: 'list', items: ['', ''] }) },
    { key: 'table', label: 'Tableau', onPress: () => append({ type: 'table', headers: ['', ''], rows: [['', '']] }) },
    { key: 'figure', label: 'Figure', onPress: () => append({ type: 'figure', emoji: '📐', label: '', caption: '' }) },
    { key: 'graph', label: 'Graphique', onPress: () => append({ type: 'graph', title: '', kind: 'bar', labels: ['A', 'B'], values: [40, 60] }) },
    { key: 'formula', label: 'Formule', onPress: () => append({ type: 'formula', text: '' }) },
    { key: 'note', label: 'Encadré', onPress: () => append({ type: 'note', variant: 'definition', text: '' }) },
  ];

  const [pickerOpen, setPickerOpen] = useState(false);

  return (
    <View style={[styles.container, { backgroundColor: colors.surfaceVariant, borderColor: colors.border }]}>
      {/* Toolbar */}
      <ScrollView horizontal showsHorizontalScrollIndicator={false} style={styles.toolbar}>
        <TouchableOpacity style={[styles.toolBtn, { backgroundColor: colors.surface, borderColor: colors.border }]} onPress={() => setPickerOpen(o => !o)}>
          <Text style={[styles.toolBtnText, { color: colors.primary }]}>＋ Ajouter un bloc</Text>
        </TouchableOpacity>
        {toolbar.map(t => (
          <TouchableOpacity
            key={t.key}
            style={[styles.toolBtn, { backgroundColor: colors.surface, borderColor: colors.border }]}
            onPress={t.action}
          >
            <Text style={[styles.toolBtnText, { color: colors.textPrimary }]}>{t.label}</Text>
          </TouchableOpacity>
        ))}
      </ScrollView>

      {/* Picker de blocs */}
      {pickerOpen && (
        <View style={[styles.pickerWrap, { backgroundColor: colors.surface, borderColor: colors.border }]}>
          {picker.map(p => (
            <TouchableOpacity
              key={p.key}
              style={[styles.pickerItem, { backgroundColor: colors.surfaceVariant }]}
              onPress={() => {
                p.onPress();
                setPickerOpen(false);
              }}
            >
              <Text style={[styles.pickerItemText, { color: colors.textPrimary }]}>{p.label}</Text>
            </TouchableOpacity>
          ))}
        </View>
      )}

      {/* Liste des blocs */}
      {blocks.length === 0 ? (
        <Text style={[styles.emptyText, { color: colors.textMuted }]}>
          Aucun bloc. Ajoutez un titre, un paragraphe, un tableau ou une figure pour composer votre leçon.
        </Text>
      ) : (
        <View style={styles.blocksList}>
          {blocks.map((block, index) => (
            <View key={index} style={[styles.blockCard, { backgroundColor: colors.surface, borderColor: colors.border }]}>
              {/* Block controls */}
              <View style={styles.blockHeader}>
                <Text style={[styles.blockType, { color: colors.primary }]}>
                  {blockLabel(block)}
                </Text>
                <View style={styles.blockActions}>
                  <Btn label="▲" onPress={() => moveAt(index, -1)} />
                  <Btn label="▼" onPress={() => moveAt(index, 1)} />
                  <Btn label="🗑" onPress={() => removeAt(index)} />
                </View>
              </View>

              {blockEditor(block, colors, {
                updateText: (text: string) => updateAt(index, { text }),
                updateAt,
                toggleNoteVariant,
                toggleListOrder,
              })}
            </View>
          ))}
        </View>
      )}
    </View>
  );
};

function blockLabel(b: RichBlock): string {
  switch (b.type) {
    case 'heading': return `Titre H${b.level}`;
    case 'paragraph': return b.bold ? 'Paragraphe (gras)' : b.italic ? 'Paragraphe (italique)' : 'Paragraphe';
    case 'list': return b.ordered ? 'Liste numérotée' : 'Liste à puces';
    case 'table': return 'Tableau';
    case 'figure': return 'Figure';
    case 'graph': return `Graphique ${b.kind === 'bar' ? 'barres' : 'lignes'}`;
    case 'formula': return 'Formule';
    case 'note': return `Encadré · ${NOTE_META[b.variant].title}`;
    default: return 'Bloc';
  }
}

const Btn: React.FC<{ label: string; onPress: () => void }> = ({ label, onPress }) => (
  <TouchableOpacity style={styles.smallBtn} onPress={onPress}>
    <Text style={styles.smallBtnText}>{label}</Text>
  </TouchableOpacity>
);

function blockEditor(
  block: RichBlock,
  colors: ThemeColorsLike,
  ctx: {
    updateText: (t: string) => void;
    updateAt: (i: number, patch: Partial<RichBlock>) => void;
    toggleNoteVariant: (i: number) => void;
    toggleListOrder: (i: number) => void;
  }
) {
  const input = (value: string, onCh: (t: string) => void, ph: string, extra?: object) => (
    <TextInput
      style={[
        styles.editInput,
        { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary },
        extra,
      ]}
      placeholder={ph}
      placeholderTextColor={colors.textMuted}
      multiline={!extra?.height}
      value={value}
      onChangeText={onCh}
    />
  );

  switch (block.type) {
    case 'heading':
      return (
        <View>
          {input(block.text, ctx.updateText, 'Titre...')}
          <RowBtns
            opts={[
              { label: 'H1', on: block.level === 1, onPress: () => ctx.updateAt(0, { level: 1 }) },
              { label: 'H2', on: block.level === 2, onPress: () => ctx.updateAt(0, { level: 2 }) },
              { label: 'H3', on: block.level === 3, onPress: () => ctx.updateAt(0, { level: 3 }) },
            ]}
          />
        </View>
      );

    case 'paragraph':
      return (
        <View>
          {input(block.text, ctx.updateText, 'Texte du paragraphe...')}
          <RowBtns
            opts={[
              { label: 'B (gras)', on: !!block.bold, onPress: () => ctx.updateAt(0, { bold: !block.bold }) },
              { label: 'I (italique)', on: !!block.italic, onPress: () => ctx.updateAt(0, { italic: !block.italic }) },
            ]}
          />
        </View>
      );

    case 'list':
      return (
        <View>
          <RowBtns
            opts={[
              { label: block.ordered ? 'Numérotée' : 'À puces', on: false, onPress: () => ctx.toggleListOrder(0) },
            ]}
          />
          {block.items.map((item, i) => (
            <View key={i} style={{ flexDirection: 'row', alignItems: 'center', gap: 6 }}>
              <Text style={{ color: colors.textMuted }}>{block.ordered ? `${i + 1}.` : '•'}</Text>
              {input(item, t => ctx.updateAt(0, { items: block.items.map((x, xi) => (xi === i ? t : x)) }), `Élément ${i + 1}`)}
              <Btn label="✕" onPress={() => ctx.updateAt(0, { items: block.items.filter((_, xi) => xi !== i) })} />
            </View>
          ))}
          <TouchableOpacity onPress={() => ctx.updateAt(0, { items: [...block.items, ''] })}>
            <Text style={{ color: colors.primary, fontWeight: '700', marginTop: 6 }}>＋ Ajouter un élément</Text>
          </TouchableOpacity>
        </View>
      );

    case 'table':
      return (
        <View>
          <Text style={[styles.miniLabel, { color: colors.textMuted }]}>En-têtes (séparés par ;)</Text>
          {input(block.headers.join(' ; '), t => ctx.updateAt(0, { headers: t.split(' ; ').map(s => s.trim()) }), 'Col A ; Col B', { height: 40 })}
          {block.rows.map((row, r) => (
            <View key={r} style={{ flexDirection: 'row', alignItems: 'center', gap: 6, marginTop: 4 }}>
              {input(row.join(' ; '), t => ctx.updateAt(0, { rows: block.rows.map((x, xi) => xi === r ? t.split(' ; ').map(s => s.trim()) : x) }), `Ligne ${r + 1}`, { height: 38, flex: 1 })}
              <Btn label="✕" onPress={() => ctx.updateAt(0, { rows: block.rows.filter((_, xi) => xi !== r) })} />
            </View>
          ))}
          <TouchableOpacity onPress={() => ctx.updateAt(0, { rows: [...block.rows, []] })}>
            <Text style={{ color: colors.primary, fontWeight: '700', marginTop: 6 }}>＋ Ajouter une ligne</Text>
          </TouchableOpacity>
        </View>
      );

    case 'figure':
      return (
        <View>
          {input(block.emoji, t => ctx.updateAt(0, { emoji: t || '📐' }), 'Emoji/icône')}
          {input(block.label, t => ctx.updateAt(0, { label: t }), 'Légende de la figure')}
          {input(block.caption || '', t => ctx.updateAt(0, { caption: t }), 'Titre de la figure (optionnel)')}
        </View>
      );

    case 'graph':
      return (
        <View>
          {input(block.title, t => ctx.updateAt(0, { title: t }), 'Titre du graphique')}
          <RowBtns
            opts={[
              { label: 'Barres', on: block.kind === 'bar', onPress: () => ctx.updateAt(0, { kind: 'bar' }) },
              { label: 'Lignes', on: block.kind === 'line', onPress: () => ctx.updateAt(0, { kind: 'line' }) },
            ]}
          />
          {input(block.labels.join(' ; '), t => ctx.updateAt(0, { labels: t.split(' ; ').map(s => s.trim()) }), 'Libellés (séparés par ;)', { height: 40 })}
          {input(block.values.join(', '), t => ctx.updateAt(0, { values: t.split(',').map(s => Number(s.trim()) || 0) }), 'Valeurs (séparées par ,)', { height: 40 })}
        </View>
      );

    case 'formula':
      return input(block.text, ctx.updateText, 'Formule (ex: a² + b² = c²)');

    case 'note':
      return (
        <View>
          <RowBtns
            opts={(Object.keys(NOTE_META) as (keyof typeof NOTE_META)[]).map(v => ({
              label: NOTE_META[v].title.slice(0, 12),
              on: block.variant === v,
              onPress: () => ctx.toggleNoteVariant(0),
            }))}
          />
          {input(block.text, ctx.updateText, 'Texte de l\'encadré...')}
        </View>
      );

    default:
      return null;
  }
}

type ThemeColorsLike = {
  surface: string;
  surfaceVariant: string;
  border: string;
  textPrimary: string;
  textSecondary: string;
  textMuted: string;
  primary: string;
  accent: string;
};

const RowBtns: React.FC<{ opts: { label: string; on: boolean; onPress: () => void }[] }> = ({ opts }) => {
  const { colors } = useAppTheme();
  return (
    <View style={styles.rowBtns}>
      {opts.map((o, i) => (
        <TouchableOpacity
          key={i}
          onPress={o.onPress}
          style={[
            styles.optChip,
            { backgroundColor: o.on ? colors.primary : colors.surfaceVariant, borderColor: o.on ? colors.primary : colors.border },
          ]}
        >
          <Text style={[styles.optChipText, { color: o.on ? '#FFFFFF' : colors.textSecondary }]}>{o.label}</Text>
        </TouchableOpacity>
      ))}
    </View>
  );
};

const styles = StyleSheet.create({
  container: { borderRadius: 12, borderWidth: 1, padding: 10, marginBottom: 14 },
  toolbar: { flexDirection: 'row', marginBottom: 8 },
  toolBtn: { paddingHorizontal: 12, paddingVertical: 8, borderRadius: 8, borderWidth: 1, marginRight: 8 },
  toolBtnText: { fontSize: 13, fontWeight: '700' },
  pickerWrap: { flexDirection: 'row', flexWrap: 'wrap', gap: 8, borderRadius: 10, borderWidth: 1, padding: 8, marginBottom: 8 },
  pickerItem: { paddingHorizontal: 10, paddingVertical: 6, borderRadius: 6 },
  pickerItemText: { fontSize: 13, fontWeight: '600' },
  emptyText: { textAlign: 'center', padding: 20, fontSize: 13, lineHeight: 20 },
  blocksList: { gap: 10 },
  blockCard: { borderRadius: 10, borderWidth: 1, padding: 10 },
  blockHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 8 },
  blockType: { fontSize: 12, fontWeight: '800' },
  blockActions: { flexDirection: 'row', gap: 4 },
  smallBtn: { backgroundColor: 'rgba(128,128,128,0.12)', paddingHorizontal: 8, paddingVertical: 4, borderRadius: 6 },
  smallBtnText: { fontSize: 12, fontWeight: '800' },
  editInput: { borderRadius: 8, borderWidth: 1, padding: 10, fontSize: 14, marginBottom: 8 },
  rowBtns: { flexDirection: 'row', flexWrap: 'wrap', gap: 6, marginBottom: 8 },
  optChip: { paddingHorizontal: 10, paddingVertical: 5, borderRadius: 6, borderWidth: 1 },
  optChipText: { fontSize: 12, fontWeight: '700' },
  miniLabel: { fontSize: 11, marginBottom: 4 },
});