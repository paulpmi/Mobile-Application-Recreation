/**
 * Created by paulp on 11/9/2017.
 */

import * as React from "react/cjs/react.production.min";
import {Button, ButtonsArray as onPress, Text, TextInput, View} from "react-native";
import {NavigationScreenProp as goBack, NavigationScreenProp as navigate} from "react-navigation";


export class EditScreen extends React.Component{

    static navigationOptions = {
        title: 'Home',
    };
    constructor(props)
    {
        super(props);
        let {params} = this.props.navigation.state;
        let a = params.data;
        this.state = { text: a }
    }

    backStock(){
        this.props.navigation.state.params.data = this.state.text;
        this.props.navigation.state.params.returnData(this.state.text);
        this.props.navigation.goBack();
    }

    render(){
        let {params} = this.props.navigation.state;
        let {navigate} = this.props.navigation;
        let {goBack} = this.props.navigation;
        let {text} = this.props.navigation;

        return (
            <View>
            <TextInput
                style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                value={this.state.text}
                onChangeText={(text) => this.setState({text})}
            />
                <Button title="Save" onPress={() => this.backStock()}/>
            </View>
        );
    }
}