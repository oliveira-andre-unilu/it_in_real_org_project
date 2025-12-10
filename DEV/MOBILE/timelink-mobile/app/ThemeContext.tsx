import React, { createContext, useState, useEffect, ReactNode } from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";

type ThemeType = "light" | "dark";

type ThemeContextType = {
    theme: ThemeType;
    toggleTheme: () => void;
};

export const ThemeContext = createContext<ThemeContextType>({
    theme: "light",
    toggleTheme: () => {},
});

type Props = { children: ReactNode };

export const ThemeProvider = ({ children }: Props) => {
    const [theme, setTheme] = useState<ThemeType>("light");

    // Load saved theme out of Storage
    useEffect(() => {
        AsyncStorage.getItem("theme").then(saved => {
            if (saved === "light" || saved === "dark") setTheme(saved);
        });
    }, []);

    // Toggle between light and dark mode
    const toggleTheme = () => {
        const next = theme === "light" ? "dark" : "light";
        setTheme(next);
        AsyncStorage.setItem("theme", next);
    };

    return (
        <ThemeContext.Provider value={{ theme, toggleTheme }}>
            {children}
        </ThemeContext.Provider>
    );
};
