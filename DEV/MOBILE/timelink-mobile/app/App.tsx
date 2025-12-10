import React from "react";
import AppNavigator from "./Navigator";
import { ThemeProvider } from "./ThemeContext";

export default function App() {
  return (
      <ThemeProvider>
        <AppNavigator />
      </ThemeProvider>
  );
}