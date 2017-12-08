/**
 * Created by paulp on 11/9/2017.
 */

import * as React from "react/cjs/react.production.min";
import {Button, FlatList, ListView, RefreshControl, Text, TextInput, View} from "react-native";
import {NavigationScreenProp as navigate, StackNavigator} from "react-navigation";
import {EditScreen} from "./EditPage";
import * as navigation from "react-navigation";
import Communications from 'react-native-communications'
import * as firebase from 'firebase';
//import * as Linking from "react-native";
//import * as Linking from "react-native";


let firebaseConfig = {
    apiKey: "<your-api-key>",
    authDomain: "<your-auth-domain>",
    databaseURL: "https://mobileapp-50d6f.firebaseio.com/",
    storageBucket: "<your-storage-bucket>",
    persistence: true
};
let firebaseApp = firebase.initializeApp(firebaseConfig);

export class HomeScreen extends React.Component
{
    static navigationOptions = {
        title: 'Profile',
        title: 'Login',
        title: 'Register',
        title: 'Chart'
    };
    constructor(props)
    {
        super(props);

        let ds = new ListView.DataSource({
            rowHasChanged: (r1, r2) => r1 !== r2
        });

        let qkjfha = ["item1","item2","item3","item4","item5"];
        this.state = {

            dataS : qkjfha,
            dataSource:ds.cloneWithRows(qkjfha),
            text:"",
            old : "",
            url: "",
            refreshing : false,
        }
    }

    returnData(id, old, name) {
        //let newDs = this.state.dataSource.splice();
        /*for (let i =0; i<this.state.dataSource; i++)
            if (this.state.dataSource[i] !== this.state.old)
                newDs.push(this.state.dataSource[i]);
            else
                newDs.push(name);

        let newDataSource = new ListView.DataSource(
            {rowHasChanged: (r1, r2) => r1 !== r2}
        );
        newDataSource.cloneWithRows(newDs);
*/

        this.setState({text:name});
        let list = [];

        for (let i = 0; i< this.state.dataS.length; i++)
            if (this.state.dataS[i] !== old)
                list.push(this.state.dataS[i]);
            else
                list.push(name);
        this.state.dataS = list;
        //list[id] = name;
        //this.state.dataS.push(old);
        this.setState({dataSource: this.state.dataSource.cloneWithRows(list)});

        this.forceUpdate();
        //this.setState({refreshing:true});
        //this.setState({refreshing:false});

    }

    _onRefresh() {
        this.setState({refreshing:true});
        this.setState({refreshing:false});
    }

    render()
    {
        let {navigate} = this.props.navigation;
        let {text} = this.state;

        return(
            <View>
                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    value={this.state.text}
                    onChangeText={(text) => this.setState({text})}
                    />
                <Button title="Submit" onPress={() =>
                    Communications.email(['paulsummerblack@gmail.com'], null, null, this.state.text)
                }/>
                <Button title="Register" onPress={() => navigate('Register', {database: firebaseApp})} />
                <Button title="Login" onPress={() => navigate('Login', {database: firebaseApp})} />
                <Button title="Chart" onPress={() => navigate('Chart', {database: firebaseApp})} />
                <ListView
                    refreshControl = {<RefreshControl
                        refreshing={this.state.refreshing}
                        onRefresh={this._onRefresh.bind(this)}
                    />}
                        dataSource={ this.state.dataSource}
                        renderRow={(rowData,sectionID, rowID) => <View size={50} color="#fff">
                            <Text onPress={() => {
                                this.setState({old:rowData});
                                navigate('Profile', {data: rowData, returnData: this.returnData.bind(this, rowID, rowData)});
                            }}>
                                {rowData}
                            </Text>
                        </View>}
                    />
            </View>
                );
    }
}