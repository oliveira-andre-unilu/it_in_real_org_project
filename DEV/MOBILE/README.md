# TIMELINK-MOBILE | TIMELINK-ADMIN
**React Native Development on Windows with WebStorm**  
(Android + iOS via Expo)

This guide explains how to set up a React Native development environment on Windows using WebStorm, run Android locally, and test iOS apps via Expo Go.

---

## 1. Prerequisites

1. **Node.js**
   * Install [Node.js LTS](https://nodejs.org/)
   * Verify:
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

4. **Environment Variables**
   * `ANDROID_HOME` → `C:\Users\<USERNAME>\AppData\Local\Android\Sdk`
   * Add to `PATH`:
     ```
     %ANDROID_HOME%\platform-tools
     %ANDROID_HOME%\emulator
     ```

5. **Expo CLI** (for iOS testing)
   ```bash
   npm install -g expo-cli
