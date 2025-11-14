# Habit Journal Mobile App

React Native mobile application for Habit Journal built with Expo.

## Prerequisites

- Node.js 18+
- Expo CLI: `npm install -g expo-cli`
- iOS Simulator (Mac) or Android Studio (for Android emulator)
- Backend API running on http://localhost:8080

## Getting Started

### Installation

```bash
cd mobile
npm install
```

### Running the App

```bash
# Start Expo development server
npm start

# Run on iOS simulator
npm run ios

# Run on Android emulator
npm run android

# Run in web browser
npm run web
```

## Project Structure

```
mobile/
├── src/
│   ├── screens/          # Screen components
│   │   ├── LoginScreen.js
│   │   ├── RegisterScreen.js
│   │   ├── DashboardScreen.js
│   │   ├── HabitsScreen.js
│   │   ├── EntriesScreen.js
│   │   └── MilestonesScreen.js
│   ├── context/          # React context
│   │   └── AuthContext.js
│   ├── services/         # API services
│   │   └── api.js
│   └── components/       # Reusable components
├── assets/               # Images, fonts, etc.
├── App.js               # Main app component
├── app.json             # Expo configuration
└── package.json         # Dependencies
```

## Features

- **Authentication** - Login and registration
- **Bottom Tab Navigation** - Easy navigation between screens
- **Habit Tracking** - View and manage habits
- **Journal Entries** - Log daily entries
- **Milestone Tracking** - Track progress toward goals
- **Offline Support** - AsyncStorage for local data

## API Configuration

Update the API base URL in `src/services/api.js`:

```javascript
const API_BASE_URL = 'http://your-api-url:8080';
```

For local development on physical device, use your computer's local IP address:
```javascript
const API_BASE_URL = 'http://192.168.1.xxx:8080';
```

## Building for Production

### iOS

```bash
expo build:ios
```

### Android

```bash
expo build:android
```

## Publishing

```bash
expo publish
```

## License

This is part of the Habit Journal project.
