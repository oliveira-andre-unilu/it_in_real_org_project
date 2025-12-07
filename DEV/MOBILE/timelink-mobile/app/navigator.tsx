import { createStackNavigator } from '@react-navigation/stack';
import Dashboard from './DashboardScreen';
import LoginScreen from './LoginScreen';
import AuthLoadingScreen from './AuthLoadingScreen';
import SettingsScreen from './SettingsScreen';
import MonitorScreen from './MonitorScreen';

const Stack = createStackNavigator();

export default function AppNavigator() {
    return (
        <Stack.Navigator initialRouteName="AuthLoading">
            <Stack.Screen name="AuthLoading" component={AuthLoadingScreen} />

            <Stack.Screen   name="Login" 
                            component={LoginScreen} 
                            options={{
                                headerShown: false,
                                headerLeft: () => null, // hides back arrow
                                gestureEnabled: false, // disables swipe to go back
                            }} />

            <Stack.Screen   name="Dashboard" 
                            component={Dashboard}
                            options={{
                                headerLeft: () => null, // hides back arrow
                                gestureEnabled: false, // disables swipe to go back
                            }} />

            <Stack.Screen name="Settings" component={SettingsScreen} />

            <Stack.Screen name="Monitor" component={MonitorScreen} />
        </Stack.Navigator>
    );
}