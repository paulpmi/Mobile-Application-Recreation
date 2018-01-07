/**
 * Created by paulp on 12/7/2017.
 */
import * as React from "react/cjs/react.production.min";
import {Button, TextInput, View} from "react-native";
//import * as firebase from 'firebase';
import Alert from "react-native";
//import * as firebase from "react-native-firebase";
//import * as admin from "react-native-firebase";
//import * as admin from "react-native-firebase";
//import * as admin from "firebase-admin"

export class LoginScreen extends React.Component {
    static navigationOptions = {
        title: 'CardScreen'
    };

    constructor(props)
    {
        super(props);
        let {params} = this.props.navigation.state;
        this.fireapp = params.database;
        this.itemsRef = this.fireapp.database().ref().child("users");
        this.authRef = this.fireapp.auth();
        //this.messageRef = this.fireapp.messaging();
            //admin.messaging(this.fireapp);
        this.state = {
            database: this.fireapp,
            username: "",
            password: ""
        };

    }

    render(){
        let {navigate} = this.props.navigation;

        return(
            <View>
                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    value={this.state.text}
                    onChangeText={(username) => this.setState({username})}
                />

                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    value={this.state.text}
                    onChangeText={(password) => this.setState({password})}
                />

                <Button title="Login" onPress={
                    () => {
                        try {
                            this.authRef.signInWithEmailAndPassword(this.state.username, this.state.password)
                                .then(() => {

                                //this.itemsRef.child("usersTokens").child(this.authRef.currentUser).set(this.messageRef.getToken());

                                navigate('CardScreen', {
                                        data: this.state.username,
                                        database: this.state.database
                                    });},
                                (error) => { alert("Wrong username or password")}
                                )
                        }
                        catch(e) { alert("Wrong username or password") }
                    }}
                />

            </View>
        );
    }
}