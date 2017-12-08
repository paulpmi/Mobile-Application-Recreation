/**
 * Created by paulp on 12/7/2017.
 */
import * as React from "react/cjs/react.production.min";
import {Button, TextInput, View} from "react-native";
import * as firebase from 'firebase';
import Alert from "react-native";

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
                        this.itemsRef.child(this.state.username).once('value', (snapshot) => {
                            let exists = (snapshot.val() !== null);
                            if (exists)
                                navigate('CardScreen', {data: this.state.username, database: this.state.database});
                            else
                                alert('Username Does not exist',
                                    'Username Does not exist',
                                    {text: 'OK', onPress: () => console.log('OK Pressed')},
                                    { cancelable: false }
                                );
                        })}}/>
            </View>
        );
    }
}