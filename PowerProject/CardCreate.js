/**
 * Created by paulp on 12/7/2017.
 */
import * as React from "react/cjs/react.production.min";
import {Button, TextInput, View} from "react-native";
import * as firebase from 'firebase';

export class CardCreateScreen extends React.Component{
    static navigationOptions = {
        title: 'RegisterScreen',
        title: 'CardScreen'
    };

    constructor(props)
    {
        super(props);
        let {params} = this.props.navigation.state;

        this.database = params.database;
        this.itemsRef = this.database.database().ref();
        let a = params.data;
        this.state = {
            cardName: "",
            cardType: "",
            cardUser: a
        };
    }

    render(){
        return(
            <View>
                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    value={this.state.text}
                    onChangeText={(cardName) => this.setState({cardName})}
                />

                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    value={this.state.text}
                    onChangeText={(cardType) => this.setState({cardType})}
                />

                <Button title="Add Card" onPress={() => {
                    this.itemsRef.child("cards").child(this.state.cardName)
                        .set({User: this.state.cardUser, Type: this.state.cardType});
                    this.itemsRef.child("users").child(this.state.cardUser).child("cards").set({Cards: this.state.cardName});
                }
                }
                />
            </View>
        );
    }
}