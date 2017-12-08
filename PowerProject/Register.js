import * as React from "react/cjs/react.production.min";
import {Button, TextInput, View} from "react-native";
import {NavigationScreenProp as navigate} from "react-navigation";

export class RegisterScreen extends React.Component{

    static navigationOptions = {
        title: 'CardScreen'
        };
    constructor(props)
    {
        super(props);
        let {params} = this.props.navigation.state;
        this.fireapp = params.database;
        this.itemsRef = this.fireapp.database().ref().child("users");
        this.itemsRef.keepSynced(true);
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

            <Button title="Register" onPress={
                () => {
                    this.itemsRef.child(this.state.username).set({
                        Password: this.state.password,
                        Email: this.state.username
                    });
                    navigate('CardScreen', {data: this.state.username, database: this.state.database});
                }
            }/>
            </View>
        );
    }
    
}