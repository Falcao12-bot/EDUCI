# EduCI — Base44 Dev Environment

## Project Overview
EduCI is an Ivorian educational platform with two frontends:
- **Android Native** (`app/`): Kotlin + Jetpack Compose. Cannot run in the web preview.
- **React Native / Expo** (`react-native-educi/`): TypeScript, Expo SDK 51. This is what runs in the preview via a production web export served by `serve-prod.js`.

The web preview runs the Expo app on port 3000. The Android native app is not part of the dev loop here.

## Setup Notes
- The `react-native-educi/` directory was missing config files (`babel.config.js`, `app.json`, `tsconfig.json`) and web dependencies (`react-dom`, `react-native-web`, `@expo/metro-runtime`). These were added to enable `expo start --web`.
- No external credentials are required for the web app. The AI teacher uses a local fallback (no Gemini API calls). `GEMINI_API_KEY` is only used by the Android native app.
- Auth is local-only via AsyncStorage — no backend or database service.
- Owner email: `horizonprogrammeur@gmail.com`, master key: `EDUCI-PROPRIETAIRE-2026`.

## Running
```bash
docker compose -f docker-compose.base44.yml up -d --build
```
The web service installs npm deps, runs `npx expo export --platform web` to create a production build (764 KB vs 3 MB dev bundle), then serves it via `node serve-prod.js` on port 3000. A file watcher rebuilds automatically when source files change. After code edits, call `reload_preview` to refresh the browser — there is no HMR WebSocket.

## Why production build instead of dev server
The Expo dev server (`expo start --web`) produces a 3 MB bundle with source maps and HMR overhead. This was too large to load on mobile browsers and external browsers (stuck on "starting your preview" / "page introuvable"). The production export is ~764 KB and loads reliably on all devices.

## Verification
- `docker compose -f docker-compose.base44.yml ps` — web service should be healthy.
- `curl -s http://localhost:3000` — should return the Expo HTML shell (1.2 KB).
- `curl -s -o /dev/null -w "%{http_code}" http://localhost:3000/_expo/static/js/web/` — JS bundle should return 200.
- First build takes ~15s; the healthcheck has a 120s start period.
