/**
 * Created by paulp on 12/7/2017.
 */
import * as React from "react/cjs/react.production.min";
import {Button, Picker, TextInput, View} from "react-native";
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
        this.itemsRef.keepSynced(true);
        let a = params.data;
        this.state = {
            cardName: "",
            cardType: "",
            cardDescription: "",
            cardMana: "",
            cardAttack: "",
            cardHealth: "",
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

                <Picker
                    selectedValue={this.state.cardType}
                    onValueChange={(itemValue, itemIndex) => this.setState({cardType: itemValue})}>
                    <Picker.Item label="Battlecry" value="Battlecry" />
                    <Picker.Item label="Deathrattle" value="Deathrattle" />
                    <Picker.Item label="Charge" value="Charge" />
                </Picker>


                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    value={this.state.text}
                    onChangeText={(cardDescription) => this.setState({cardDescription})}
                />

                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    value={this.state.text}
                    onChangeText={(cardMana) => this.setState({cardMana})}
                />

                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    value={this.state.text}
                    onChangeText={(cardAttack) => this.setState({cardAttack})}
                />

                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    value={this.state.text}
                    onChangeText={(cardHealth) => this.setState({cardHealth})}
                />

                <Button title="Add Card" onPress={() => {
                    this.itemsRef.child("cards").child(this.state.cardName)
                        .set({User: this.state.cardUser, Type: this.state.cardType
                            , Description: this.state.cardDescription, Mana: this.state.cardMana
                            , Health: this.state.cardHealth, Attack: this.state.cardAttack
                        });
                    this.itemsRef.child("users").child(this.state.cardUser).child("cards").set({Cards: this.state.cardName});

                }
                }
                />
            </View>
        );
    }
}