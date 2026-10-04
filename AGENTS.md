# EduCI — Base44 Dev Environment

## Project Overview
EduCI is an Ivorian educational platform with two frontends:
- **Android Native** (`app/`): Kotlin + Jetpack Compose. Cannot run in the web preview.
- **React Native / Expo** (`react-native-educi/`): TypeScript, Expo SDK 51. This is what runs in the preview via `expo start --web`.

The web preview runs the Expo app on port 3000. The Android native app is not part of the dev loop here.

## Setup Notes
- The `react-native-educi/` directory was missing config files (`babel.config.js`, `app.json`, `tsconfig.json`) and web dependencies (`react-dom`, `react-native-web`, `@expo/metro-runtime`). These were added to enable `expo start --web`.
- No external credentials are required for the web app. The AI teacher uses a local fallback (no Gemini API calls). `GEMINI_API_KEY` is only used by the Android native app.
- Auth is local-only via AsyncStorage — no backend or database service.
- The share/open/download link on `InstallMobileScreen` uses `EXPO_PUBLIC_APP_URL` (permanent public URL, inlined at build time), else `window.location.origin` on web. It used to be hardcoded to an expired Google AI Studio Cloud Run URL, which returned 404.
- Owner email: `horizonprogrammeur@gmail.com`, master key: `EDUCI-PROPRIETAIRE-2026`.

## Running
```bash
docker compose -f docker-compose.base44.yml up -d --build
```
The web service installs npm deps on startup, then runs `expo start --web --port 3000 --host 0.0.0.0`.

## Verification
- `docker compose -f docker-compose.base44.yml ps` — web service should be healthy.
- `curl -s http://localhost:3000` — should return the Expo HTML shell.
- First bundle may take 30–60s; the healthcheck has a 120s start period.
