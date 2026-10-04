const http = require('http');
const fs = require('fs');
const path = require('path');
const { exec } = require('child_process');

const DIST_DIR = path.join(__dirname, 'dist');
const PORT = 3000;

const MIME = {
  '.html': 'text/html',
  '.js': 'application/javascript; charset=UTF-8',
  '.css': 'text/css',
  '.png': 'image/png',
  '.jpg': 'image/jpeg',
  '.svg': 'image/svg+xml',
  '.json': 'application/json',
  '.ico': 'image/x-icon',
  '.woff': 'font/woff',
  '.woff2': 'font/woff2',
  '.ttf': 'font/ttf',
  '.map': 'application/json',
};

const server = http.createServer((req, res) => {
  const urlPath = req.url.split('?')[0];
  let filePath = path.join(DIST_DIR, urlPath);

  if (!fs.existsSync(filePath) || fs.statSync(filePath).isDirectory()) {
    filePath = path.join(DIST_DIR, 'index.html');
  }

  const ext = path.extname(filePath);
  res.writeHead(200, {
    'Content-Type': MIME[ext] || 'application/octet-stream',
    'Cache-Control': ext === '.html' ? 'no-cache' : 'public, max-age=3600',
  });
  fs.createReadStream(filePath).pipe(res);
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`Serving production build on http://0.0.0.0:${PORT}`);
});

// --- File watcher: rebuild on source changes ---
let rebuilding = false;
let debounceTimer = null;

function rebuild() {
  if (rebuilding) return;
  clearTimeout(debounceTimer);
  debounceTimer = setTimeout(() => {
    rebuilding = true;
    console.log('[watch] Source changed — rebuilding web...');
    exec('npx expo export --platform web', { cwd: __dirname }, (err, stdout, stderr) => {
      if (err) {
        console.error('[watch] Build failed:', stderr.slice(0, 500));
      } else {
        console.log('[watch] Build complete');
      }
      rebuilding = false;
    });
  }, 800);
}

const watchTargets = ['src', 'App.tsx', 'app.json', 'babel.config.js'];
watchTargets.forEach((target) => {
  const full = path.join(__dirname, target);
  if (fs.existsSync(full)) {
    try {
      fs.watch(full, { recursive: true }, rebuild);
    } catch (e) {
      // recursive watch may not be supported on all platforms
      if (fs.statSync(full).isDirectory()) {
        fs.readdirSync(full).forEach((f) => {
          const fp = path.join(full, f);
          if (fs.statSync(fp).isDirectory()) {
            try { fs.watch(fp, { recursive: true }, rebuild); } catch (_) {}
          } else {
            try { fs.watch(fp, rebuild); } catch (_) {}
          }
        });
      }
    }
  }
});
