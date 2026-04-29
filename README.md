# CinemaOS TV — Android TV App

A fullscreen Android TV WebView app for [cinemaos.live](https://cinemaos.live) with full D-pad navigation support.

## Features
- ✅ Full D-pad navigation (Up/Down/Left/Right + OK/Select)
- ✅ Back button support
- ✅ Red focus highlight ring on selected elements
- ✅ Fullscreen, no browser UI
- ✅ Video playback support

---

## How to Build & Install (No Android Studio Needed)

### Step 1 — Create a GitHub Account
Go to [github.com](https://github.com) and sign up for a free account if you don't have one.

### Step 2 — Create a New Repository
1. Click the **+** button (top right) → **New repository**
2. Name it: `cinemaos-tv`
3. Set it to **Public**
4. Click **Create repository**

### Step 3 — Upload All Files
1. On your new repo page, click **uploading an existing file**
2. Drag and drop the entire contents of this folder (all files and folders)
3. Click **Commit changes**

> ⚠️ Make sure the folder structure is correct. The `.github/workflows/build.yml` file must be inside `.github/workflows/` — GitHub needs that exact path.

### Step 4 — Watch the Build
1. Click the **Actions** tab on your repo
2. You'll see **"Build CinemaOS TV APK"** running (yellow circle = in progress)
3. Wait ~3–5 minutes for it to finish (green checkmark ✅)

### Step 5 — Download the APK
1. Click on the finished workflow run
2. Scroll down to **Artifacts**
3. Click **CinemaOS-TV-APK** to download the zip
4. Unzip it — you'll find `app-release.apk` inside

### Step 6 — Sideload to Android TV
**On your Android TV:**
1. Go to **Settings → Device Preferences → Security & Restrictions**
2. Enable **Unknown Sources** (or "Install unknown apps")

**Transfer the APK using one of these methods:**
- 📱 **Send Files to TV** app (easiest) — install on both your phone and TV
- 🔌 **USB drive** — copy APK to USB, plug into TV, open with a file manager
- ☁️ **Google Drive** — upload APK, open Drive on TV, download and install

**Install:**
1. Open the APK file on your TV using a file manager (like **FX File Explorer**)
2. Tap **Install**
3. Find **CinemaOS** in your TV's app list and launch it!

---

## D-pad Controls
| Button | Action |
|--------|--------|
| ↑ ↓ ← → | Move focus between elements |
| OK / Enter | Click selected element |
| Back | Go back / previous page |

---

## Troubleshooting
- **Site not loading?** Check your internet connection on the TV
- **D-pad not working well on a page?** Press Back and re-enter — the JS re-injects on each page load
- **Video not playing?** Some streams require an external player; long-press OK on the video to open options
