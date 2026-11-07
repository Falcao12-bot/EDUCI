import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useAppTheme } from '../context/ThemeContext';

type Block =
  | { kind: 'heading'; level: number; text: string }
  | { kind: 'paragraph'; text: string }
  | { kind: 'list'; ordered: boolean; items: string[] };

/** Découpe un texte léger-markdown (###, **gras**, *italique*, listes) en blocs. */
function parseMarkdown(markdown: string): Block[] {
  const blocks: Block[] = [];
  let paragraphLines: string[] = [];
  let list: { ordered: boolean; items: string[] } | null = null;

  const flushParagraph = () => {
    if (paragraphLines.length > 0) {
      blocks.push({ kind: 'paragraph', text: paragraphLines.join(' ') });
      paragraphLines = [];
    }
  };

  const flushList = () => {
    if (list) {
      blocks.push({ kind: 'list', ordered: list.ordered, items: list.items });
      list = null;
    }
  };

  (markdown || '').replace(/\r/g, '').split('\n').forEach(rawLine => {
    const line = rawLine.trim();

    if (!line) {
      flushParagraph();
      flushList();
      return;
    }

    const heading = line.match(/^(#{1,6})\s+(.*)$/);
    if (heading) {
      flushParagraph();
      flushList();
      blocks.push({ kind: 'heading', level: Math.min(heading[1].length, 3), text: heading[2] });
      return;
    }

    const bullet = line.match(/^[-*•]\s+(.*)$/);
    if (bullet) {
      flushParagraph();
      if (!list || list.ordered) {
        flushList();
        list = { ordered: false, items: [] };
      }
      list.items.push(bullet[1]);
      return;
    }

    const numbered = line.match(/^\d+[.)]\s+(.*)$/);
    if (numbered) {
      flushParagraph();
      if (!list || !list.ordered) {
        flushList();
        list = { ordered: true, items: [] };
      }
      list.items.push(numbered[1]);
      return;
    }

    flushList();
    paragraphLines.push(line);
  });

  flushParagraph();
  flushList();
  return blocks;
}

/** Rendu inline : **gras**, *italique* et `code`. */
function renderInline(text: string) {
  const parts = text.split(/(\*\*[^*]+\*\*|\*[^*]+\*|`[^`]+`)/g).filter(part => part !== '');

  return parts.map((part, index) => {
    if (part.startsWith('**') && part.endsWith('**') && part.length > 4) {
      return (
        <Text key={index} style={styles.bold}>
          {part.slice(2, -2)}
        </Text>
      );
    }
    if (part.startsWith('`') && part.endsWith('`') && part.length > 2) {
      return (
        <Text key={index} style={styles.code}>
          {part.slice(1, -1)}
        </Text>
      );
    }
    if (part.startsWith('*') && part.endsWith('*') && part.length > 2) {
      return (
        <Text key={index} style={styles.italic}>
          {part.slice(1, -1)}
        </Text>
      );
    }
    return <Text key={index}>{part}</Text>;
  });
}

/**
 * MarkdownText — affiche un contenu rédigé en markdown simple sans montrer la
 * syntaxe brute (#, **) comme le faisaient les anciens écrans.
 */
export const MarkdownText: React.FC<{ content?: string }> = ({ content }) => {
  const { colors } = useAppTheme();
  const blocks = parseMarkdown(content || '');

  if (blocks.length === 0) return null;

  return (
    <View>
      {blocks.map((block, index) => {
        if (block.kind === 'heading') {
          const style =
            block.level === 1 ? styles.h1 : block.level === 2 ? styles.h2 : styles.h3;
          return (
            <Text key={index} style={[style, { color: colors.textPrimary }]}>
              {renderInline(block.text)}
            </Text>
          );
        }

        if (block.kind === 'list') {
          return (
            <View key={index} style={styles.listBlock}>
              {block.items.map((item, itemIndex) => (
                <View key={itemIndex} style={styles.listRow}>
                  <Text style={[styles.listBullet, { color: colors.primary }]}>
                    {block.ordered ? `${itemIndex + 1}.` : '•'}
                  </Text>
                  <Text style={[styles.listText, { color: colors.textPrimary }]}>
                    {renderInline(item)}
                  </Text>
                </View>
              ))}
            </View>
          );
        }

        return (
          <Text key={index} style={[styles.paragraph, { color: colors.textPrimary }]}>
            {renderInline(block.text)}
          </Text>
        );
      })}
    </View>
  );
};

const styles = StyleSheet.create({
  h1: { fontSize: 19, fontWeight: '800', marginTop: 14, marginBottom: 8 },
  h2: { fontSize: 16, fontWeight: '700', marginTop: 12, marginBottom: 6 },
  h3: { fontSize: 15, fontWeight: '700', marginTop: 10, marginBottom: 5 },
  paragraph: { fontSize: 14, lineHeight: 22, marginBottom: 12 },
  bold: { fontWeight: '800' },
  italic: { fontStyle: 'italic' },
  code: { fontFamily: 'monospace' },
  listBlock: { marginBottom: 12, gap: 6 },
  listRow: { flexDirection: 'row', alignItems: 'flex-start', gap: 8 },
  listBullet: { fontSize: 14, fontWeight: '700', width: 18 },
  listText: { fontSize: 14, lineHeight: 21, flex: 1 },
});
