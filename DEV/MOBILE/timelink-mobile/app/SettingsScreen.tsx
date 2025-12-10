import React, { useContext } from "react";
import Icon from "react-native-vector-icons/Ionicons";
import {View, Text, TouchableOpacity, StyleSheet, SafeAreaView, Alert} from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { ThemeContext } from "./ThemeContext";

// @ts-ignore
const SettingsScreen = ({ navigation }) => {
    const { theme, toggleTheme } = useContext(ThemeContext);

    const isDark = theme === "dark";
    const styles = themedStyles(isDark);
    const iconColor = isDark ? "#eee" : "#333";

    const handleLogout = async () => {
        Alert.alert(
            "Logout",
            "Are you sure you want to log out?",
            [
                { text: "Cancel", style: "cancel" },
                {
                    text: "Log out",
                    style: "destructive",
                    onPress: async () => {
                        await AsyncStorage.removeItem("authToken");
                        navigation.replace("Login");
                    }
                }
            ]
        );
    };

    return (
        <SafeAreaView style={styles.container}>
            <Text style={styles.title}>Settings</Text>

            <View style={styles.content}>
                <TouchableOpacity style={styles.button} onPress={toggleTheme}>
                    <Text style={styles.buttonText}>
                        Switch to {isDark ? "Light" : "Dark"} Mode
                    </Text>
                </TouchableOpacity>

                <TouchableOpacity
                    style={[styles.button, { backgroundColor: "#d9534f" }]}
                    onPress={handleLogout}
                >
                    <Text style={styles.buttonText}>Log Out</Text>
                </TouchableOpacity>
            </View>

            <View style={styles.navbar}>
                <TouchableOpacity
                    style={styles.navItem}
                    onPress={() => navigation.navigate("Monitor")}
                >
                    <Icon name="stats-chart-sharp" size={28} color={iconColor} />
                    <Text style={styles.navText}>Monitor</Text>
                </TouchableOpacity>

                <TouchableOpacity
                    style={styles.navItem}
                    onPress={() => navigation.navigate("Dashboard")}
                >
                    <Icon name="speedometer-outline" size={28} color={iconColor} />
                    <Text style={styles.navText}>Dashboard</Text>
                </TouchableOpacity>

                <TouchableOpacity
                    style={styles.navItem}
                    onPress={() => navigation.navigate("Settings")}
                >
                    <Icon name="settings-outline" size={28} color={iconColor} />
                    <Text style={styles.navText}>Settings</Text>
                </TouchableOpacity>
            </View>
        </SafeAreaView>
    );
};

const themedStyles = (dark: boolean) =>
    StyleSheet.create({
        container: {
            flex: 1,
            backgroundColor: dark ? "#000" : "#fff",
            justifyContent: "center",
            paddingHorizontal: 30
        },
        title: {
            fontSize: 32,
            fontWeight: "600",
            textAlign: "center",
            marginBottom: 40,
            color: dark ? "#fff" : "#000"
        },
        content: {
            flex: 1,
            justifyContent: "center",
            alignItems: "center"
        },
        button: {
            marginTop: 20,
            backgroundColor: "#9e9e9eff",
            padding: 15,
            borderRadius: 10,
            width: "100%"
        },
        buttonText: {
            color: "#fff",
            textAlign: "center",
            fontWeight: "600",
            fontSize: 16
        },
        navbar: {
            flexDirection: "row",
            justifyContent: "space-around",
            paddingVertical: 15,
            borderTopWidth: 1,
            borderTopColor: dark ? "#333" : "#eee",
            backgroundColor: dark ? "#111" : "#fafafa"
        },
        navItem: {
            alignItems: "center"
        },
        navText: {
            fontSize: 12,
            color: dark ? "#eee" : "#333",
            marginTop: 4
        }
    });

export default SettingsScreen;
