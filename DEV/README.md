# TIMELINK-MOBILE | TIMELINK-ADMIN
# React Native Development on Windows with WebStorm (Android + iOS via Expo) 

This guide walks you through setting up a React Native development environment on Windows using WebStorm, running Android locally, and testing iOS apps via Expo Go.

---

## 1️⃣ Prerequisites

1. **Node.js**

   * Download and install [Node.js LTS](https://nodejs.org/)
   * Verify installation:

     ```bash
     node -v
     npm -v
     ```

2. **Java JDK**

   * Required for Android builds
   * Install JDK 11+ (e.g., [Adoptium](https://adoptium.net/))

3. **Android Studio**

   * Download: [Android Studio](https://developer.android.com/studio)
   * During installation:

     * Install **Android SDK**
     * Install **Android Virtual Device (AVD)**
   * Default SDK path on Windows:

     ```
     C:\Users\<USERNAME>\AppData\Local\Android\Sdk
     ```

4. **Set Environment Variables**

   * `ANDROID_HOME` → `C:\Users\<USERNAME>\AppData\Local\Android\Sdk`
   * Add to `PATH`:

     ```
     %ANDROID_HOME%\platform-tools
     %ANDROID_HOME%\emulator
     ```

5. **Expo CLI (for iOS testing)**

   ```bash
   npm install -g expo-cli
   ```

---

## 2️⃣ Open Project in WebStorm

1. Open WebStorm → **Open** → select your project folder.
2. Use WebStorm **terminal** (bottom panel) to run all commands.

---

## 3️⃣ Create a New Project (Already done)

### Option A – React Native CLI (Android)

```bash
npx react-native init MyProject
cd MyProject
```

### Option B – Expo (Android + iOS)

```bash
npx create-expo-app MyProject
cd MyProject
```

> **Tip:** Expo simplifies iPhone testing without a Mac.

---

## 4️⃣ Run Android App

1. Start Metro Bundler:

```bash
npx react-native start
```

2. Start Android Emulator (Android Studio → AVD Manager → Start device)

3. Install and run app:

```bash
npx react-native run-android
```

* Or connect a **real Android device via USB** (enable USB Debugging)

---

## 5️⃣ Run iOS App (Windows)

* Direct iOS builds **cannot run on Windows** because `xcodebuild` only exists on macOS.
* Use **Expo Go App**:

  1. Install **Expo Go** from App Store on your iPhone
  2. Start project in WebStorm terminal:

     ```bash
     npx expo start
     ```
  3. Scan QR code → app runs directly on iPhone

> You can develop and test iOS features without a Mac using Expo.

---

## 6️⃣ Run Web Version (Optional)

```bash
npm run web
```

* Opens the app in your browser
* Useful for **quick testing without Android/iOS devices**

---

## 7️⃣ Useful NPM Scripts

In `package.json`, common scripts may include:

```json
"scripts": {
  "android": "npx react-native run-android",
  "ios": "npx react-native run-ios",
  "web": "npm run web",
  "start": "npx react-native start"
}
```

* `npm run android` → run Android app
* `npm run ios` → run iOS (Mac only)
* `npm run web` → run in browser
* `npm start` → start Metro Bundler

---

## 8️⃣ Debugging in WebStorm

* Use **WebStorm Debug Configurations** for Node.js
* Metro Bundler runs in terminal
* Hot Reload / Fast Refresh works for both Android and Expo iOS

---

## 9️⃣ Notes & Best Practices

* iOS builds require **Mac or cloud service** (EAS Build, Codemagic, Bitrise)
* Gradle warnings in Android are usually harmless
* Avoid storing projects on **OneDrive**; local paths like `C:\Dev\MyProject` are safer
* Expo apps are **still React Native apps**, with additional APIs and easy iOS testing

---

## 10️⃣ Optional: Ejecting Expo

If you later need full native iOS access:

```bash
npx expo eject
```

* Converts Expo project to standard React Native project
* Gives full access to Xcode & native code
* Can still use Expo APIs if desired
