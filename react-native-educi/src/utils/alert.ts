import { Platform, Alert } from 'react-native';

/**
 * Affiche une boîte de dialogue de confirmation (Oui/Non).
 * Sur web, utilise window.confirm car Alert.alert est un no-op dans react-native-web.
 * Retourne true si l'utilisateur confirme, false sinon.
 */
export const confirmDialog = (title: string, message: string): Promise<boolean> => {
  if (Platform.OS === 'web') {
    return Promise.resolve(window.confirm(`${title}\n\n${message}`));
  }
  return new Promise(resolve => {
    Alert.alert(title, message, [
      { text: 'Annuler', style: 'cancel', onPress: () => resolve(false) },
      { text: 'Confirmer', style: 'destructive', onPress: () => resolve(true) },
    ]);
  });
};

/**
 * Affiche un message simple (alerte d'information).
 * Sur web, utilise window.alert car Alert.alert est un no-op dans react-native-web.
 */
export const alertMessage = (title: string, message?: string): void => {
  if (Platform.OS === 'web') {
    window.alert(message ? `${title}\n\n${message}` : title);
  } else {
    Alert.alert(title, message);
  }
};
