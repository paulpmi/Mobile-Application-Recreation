/**
 * Created by paulp on 1/6/2018.
 */

import {Button, View} from "react-native";
import * as React from "react/cjs/react.production.min";

export class LikeScreen extends React.Component{
    constructor(props)
    {
        super(props);
        let {params} = this.props.navigation.state;
        this.fireapp = params.database;
        //this.user = params.user;
        this.cardName = params.cardName;
        this.creator = params.creator;
        this.itemsRef = this.fireapp.database().ref();
        this.saveRef = this.fireapp.database().ref();
        //console.log("passedITEMS");
        this.authRef = this.fireapp.auth();
        console.log("CARDNAME: ", this.cardName);

        //this.itemsRef.keepSynced(true);

        this.state = {
            database: this.fireapp,
            username: "",
            password: "",
            likes1: 0
        };
    }

    render(){
        let {navigate} = this.props.navigation;

        return(
            <View>

                <Button title="Like" onPress={
                    () => {
                        let like = 0;
                        let likes = this.itemsRef.child("cards").child(this.cardName).on( 'value', function(snap) {

                            console.log("SNAP: ", snap);
                            console.log("SNAPLIKE: ", snap.val().likes);

                            like = snap.val().likes;
                            like++;
                            //snap.val().likes.set(like);

                            //this.setState({likes1: like}, () => this.itemsRef.child("cards").child(this.cardName).child("likes").set(like));

                            return like;
                        });

                        this.itemsRef.child("cards").child(this.cardName).child("likes").set(like);
                    }
                }/>

            </View>
        );
    }
}
