import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { HomeScreen} from "./HomePage";
import {DrawerViewPropsExceptRouter as navigation, StackNavigator, TabNavigator} from "react-navigation";
import {EditScreen} from "./EditPage";
import {RegisterScreen} from "./Register";
import {CardCreateScreen} from "./CardCreate";
import {LoginScreen} from "./Login";
import {ChartScreen} from "./ChartScreen";
import {CardList} from "./CardList";
import {LikeScreen} from "./LikeScreen";

const App = StackNavigator({
    Home: {screen: HomeScreen},
    Profile: { screen: EditScreen },
    Register: { screen: RegisterScreen },
    CardScreen: {screen: CardCreateScreen},
    Login: {screen: LoginScreen},
    Chart: {screen: ChartScreen},
    CardList: {screen: CardList},
    LikeScreen: {screen: LikeScreen}}
);

export default App;

/*export default class App extends React.Component {
 render() {
 return (
 <View style={styles.container}>
 <HomeScreen navigation={navigation}/>
 </View>
 );
 }
 }
 */
const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
});
