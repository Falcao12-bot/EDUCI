import React, { createContext, useContext, useEffect, useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Modal } from 'react-native';
import { useAppTheme } from './ThemeContext';

interface ToastState {
  title: string;
  message?: string;
}

interface ConfirmState {
  title: string;
  message: string;
  confirmLabel: string;
  onConfirm: () => void;
}

interface FeedbackContextType {
  notify: (title: string, message?: string) => void;
  confirmAction: (
    title: string,
    message: string,
    onConfirm: () => void,
    confirmLabel?: string
  ) => void;
}

const FeedbackContext = createContext<FeedbackContextType>({
  notify: () => {},
  confirmAction: () => {},
});

/**
 * FeedbackProvider — messages et confirmations intégrés à l'application.
 * `Alert` de React Native n'affiche rien sur le web et les boîtes du
 * navigateur (alert/confirm) sont bloquées dans un aperçu intégré : ces
 * composants garantissent un retour visible partout.
 */
export const FeedbackProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { colors } = useAppTheme();
  const [toast, setToast] = useState<ToastState | null>(null);
  const [confirmState, setConfirmState] = useState<ConfirmState | null>(null);

  useEffect(() => {
    if (!toast) return;
    const timer = setTimeout(() => setToast(null), 4500);
    return () => clearTimeout(timer);
  }, [toast]);

  const notify = (title: string, message?: string) => setToast({ title, message });

  const confirmAction = (
    title: string,
    message: string,
    onConfirm: () => void,
    confirmLabel = 'Confirmer'
  ) => setConfirmState({ title, message, confirmLabel, onConfirm });

  const dismissConfirm = () => setConfirmState(null);

  return (
    <FeedbackContext.Provider value={{ notify, confirmAction }}>
      <View style={styles.root}>
        {children}

        {toast && (
          <View pointerEvents="box-none" style={styles.toastWrap}>
            <View style={[styles.toast, { backgroundColor: colors.surface, borderColor: colors.primary }]}>
              <View style={{ flex: 1 }}>
                <Text style={[styles.toastTitle, { color: colors.primary }]}>{toast.title}</Text>
                {toast.message ? (
                  <Text style={[styles.toastMessage, { color: colors.textPrimary }]}>{toast.message}</Text>
                ) : null}
              </View>
              <TouchableOpacity onPress={() => setToast(null)} accessibilityLabel="Fermer le message">
                <Text style={[styles.toastClose, { color: colors.textMuted }]}>✕</Text>
              </TouchableOpacity>
            </View>
          </View>
        )}
      </View>

      <Modal visible={!!confirmState} transparent animationType="fade" onRequestClose={dismissConfirm}>
        <View style={styles.overlay}>
          <View style={[styles.dialog, { backgroundColor: colors.surface, borderColor: colors.border }]}>
            <Text style={[styles.dialogTitle, { color: colors.textPrimary }]}>{confirmState?.title}</Text>
            <Text style={[styles.dialogMessage, { color: colors.textSecondary }]}>{confirmState?.message}</Text>
            <View style={styles.dialogActions}>
              <TouchableOpacity
                style={[styles.dialogBtn, { backgroundColor: colors.surfaceVariant, borderColor: colors.border }]}
                onPress={dismissConfirm}
              >
                <Text style={[styles.dialogBtnText, { color: colors.textPrimary }]}>Annuler</Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={[styles.dialogBtn, { backgroundColor: colors.primary, borderColor: colors.primary }]}
                onPress={() => {
                  const action = confirmState?.onConfirm;
                  setConfirmState(null);
                  if (action) action();
                }}
              >
                <Text style={[styles.dialogBtnText, { color: '#FFFFFF' }]}>
                  {confirmState?.confirmLabel}
                </Text>
              </TouchableOpacity>
            </View>
          </View>
        </View>
      </Modal>
    </FeedbackContext.Provider>
  );
};

export const useFeedback = () => useContext(FeedbackContext);

const styles = StyleSheet.create({
  root: { flex: 1 },
  toastWrap: { position: 'absolute', top: 0, left: 0, right: 0, paddingHorizontal: 12, paddingTop: 12, zIndex: 9999 },
  toast: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    gap: 10,
    padding: 12,
    borderRadius: 12,
    borderWidth: 1,
    shadowColor: '#000',
    shadowOpacity: 0.12,
    shadowRadius: 10,
    shadowOffset: { width: 0, height: 4 },
    elevation: 6,
  },
  toastTitle: { fontSize: 13, fontWeight: '800' },
  toastMessage: { fontSize: 12, lineHeight: 17, marginTop: 2 },
  toastClose: { fontSize: 14, paddingHorizontal: 4 },
  overlay: { flex: 1, backgroundColor: 'rgba(0,0,0,0.45)', alignItems: 'center', justifyContent: 'center', padding: 24 },
  dialog: { width: '100%', maxWidth: 420, padding: 20, borderRadius: 16, borderWidth: 1 },
  dialogTitle: { fontSize: 16, fontWeight: '800', marginBottom: 8 },
  dialogMessage: { fontSize: 13, lineHeight: 19, marginBottom: 18 },
  dialogActions: { flexDirection: 'row', justifyContent: 'flex-end', gap: 10 },
  dialogBtn: { paddingVertical: 10, paddingHorizontal: 16, borderRadius: 10, borderWidth: 1 },
  dialogBtnText: { fontSize: 14, fontWeight: '700' },
});
