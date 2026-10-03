import React, { useEffect, useState } from 'react';

/**
 * Configure l'application web comme PWA installable (icône sur l'écran d'accueil).
 * - Injecte le manifest, les méta tags et les icônes dans le <head> HTML
 * - Enregistre le service worker pour le mode hors-ligne
 * - Capture l'événement `beforeinstallprompt` pour proposer l'installation directe
 *
 * Sur native (Android/iOS), ce composant ne fait rien.
 */
export function usePwaInstall() {
  const [installPromptEvent, setInstallPromptEvent] = useState<any>(null);
  const [installed, setInstalled] = useState(false);

  useEffect(() => {
    if (typeof document === 'undefined') return;

    // 1. Injecter le manifest dans le <head>
    const ensureLink = (rel: string, href: string, attrs?: Record<string, string>) => {
      let el = document.querySelector(`link[rel="${rel}"]`) as HTMLLinkElement | null;
      if (!el) {
        el = document.createElement('link');
        el.rel = rel;
        document.head.appendChild(el);
      }
      el.href = href;
      if (attrs) {
        for (const [k, v] of Object.entries(attrs)) el.setAttribute(k, v);
      }
    };

    ensureLink('manifest', '/manifest.json');
    ensureLink('apple-touch-icon', '/apple-touch-icon.png');
    ensureLink('icon', '/favicon-32.png', { sizes: '32x32', type: 'image/png' });
    ensureLink('icon', '/favicon-48.png', { sizes: '48x48', type: 'image/png' });

    // 2. Injecter les méta tags
    const ensureMeta = (name: string, content: string) => {
      let el = document.querySelector(`meta[name="${name}"]`) as HTMLMetaElement | null;
      if (!el) {
        el = document.createElement('meta');
        el.name = name;
        document.head.appendChild(el);
      }
      el.content = content;
    };

    ensureMeta('theme-color', '#008751');
    ensureMeta('apple-mobile-web-app-capable', 'yes');
    ensureMeta('apple-mobile-web-app-status-bar-style', 'default');
    ensureMeta('apple-mobile-web-app-title', 'EduCI');
    ensureMeta('mobile-web-app-capable', 'yes');

    // 3. Enregistrer le service worker
    if ('serviceWorker' in navigator) {
      navigator.serviceWorker.register('/sw.js').catch(() => {});
    }

    // 4. Détecter si déjà installée (mode standalone)
    const isStandalone =
      window.matchMedia('(display-mode: standalone)').matches ||
      (navigator as any).standalone === true;
    if (isStandalone) setInstalled(true);

    // 5. Capturer l'événement d'installation
    const handler = (e: Event) => {
      e.preventDefault();
      setInstallPromptEvent(e);
    };
    window.addEventListener('beforeinstallprompt', handler);

    const installedHandler = () => setInstalled(true);
    window.addEventListener('appinstalled', installedHandler);

    return () => {
      window.removeEventListener('beforeinstallprompt', handler);
      window.removeEventListener('appinstalled', installedHandler);
    };
  }, []);

  const promptInstall = async (): Promise<boolean> => {
    if (!installPromptEvent) return false;
    installPromptEvent.prompt();
    const choice = await installPromptEvent.userChoice;
    setInstallPromptEvent(null);
    return choice.outcome === 'accepted';
  };

  return { canInstall: !!installPromptEvent && !installed, installed, promptInstall };
};
