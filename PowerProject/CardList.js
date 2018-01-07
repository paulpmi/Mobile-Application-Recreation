/**
 * Created by paulp on 1/6/2018.
 */

import * as React from "react/cjs/react.production.min";
import {Button, ListItem, ListView, Picker, Text, TextInput, View} from "react-native";
//import {NavigationScreenProp as navigate} from "react-navigation";

export class CardList extends React.Component{
    static navigationOption = {
        title: 'LikeScreen'
    };

    constructor(props)
    {
        super(props);
        let {params} = this.props.navigation.state;

        this.database = params.database;
        this.itemsRef = this.database.database().ref().child("cards");
        //this.itemsRef.keepSynced(true);
        //let a = params.data;

        let ds = new ListView.DataSource({
            rowHasChanged: (r1, r2) => r1 !== r2
        });

        //let qkjfha = ["item1","item2","item3","item4","item5"];

        this.state = {
            cardName: "",
            cardType: "",
            cardDescription: "",
            cardMana: "",
            cardAttack: "",
            cardHealth: "",
            cardUser: '',
            dataSource: ds,
        };

        this.listenForItems();
    }

    listenForItems() {
        this.itemsRef.on('value', (snap) => {

            let items = [];
            snap.forEach((child) => {
                console.log("CARD: " + child.val().name + ' ' + child.val().type + ' ' + child.val().user);
                items.push({
                    cardName: child.val().name,
                    cardType: child.val().type,
                    user: child.val().user,
                    _key: child.key
                });
            });

            console.log("LISTITEMS " + items.length );

            this.setState({
                dataSource: this.state.dataSource.cloneWithRows(items)
                //dataSource: items
            }, () => {console.log("DATASOURCEEXTRA " + this.state.dataSource.length );});


        });
    }

    _renderItem(item) {

        return (
            <ListItem item={item}/>
        );
    }

    componentDidMount() {
        this.listenForItems();
    }

    render(){
        console.log("DATASOURCE" + this.state.dataSource.length);
        let {navigate} = this.props.navigation;
        return(
            <View>
                <ListView
                    dataSource={this.state.dataSource}
                    renderRow={(rowData,sectionID, rowID) => <View size={50} color="#fff">
                        <Text onPress={() => navigate('LikeScreen',
                            {database: this.database, cardName: rowData.cardName, creator: rowData.user})}>
                            {rowData.cardName} {rowData.cardType} {rowData.user}
                        </Text>
                    </View>}
                    enableEmptySections={true}/>
            </View>
        );
    }
}