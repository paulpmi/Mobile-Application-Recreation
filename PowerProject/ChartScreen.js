/**
 * Created by paulp on 12/8/2017.
 */
/**
 * Created by paulp on 12/8/2017.
 */

import * as React from "react/cjs/react.production.min";
import Svg,{
    Line
} from 'react-native-svg';

import { AreaChart } from 'react-native-svg-charts'
import {Component, View} from 'react-native';
//import {PropTypes} from 'react';
import shape from 'd3-shape'
//import Chart from 'react-native-s';

const data = [[
    [0, 1],
    [1, 3],
    [3, 7],
    [4, 9],
]];

export class ChartScreen extends React.Component {
    static navigationOptions = {
        title: 'HomeScreen'
    };

    constructor(props)
    {
        super(props);
        let {params} = this.props.navigation.state;

        this.database = params.database;
        this.itemsRef = this.database.database().ref().child("cards");
        //this.itemsRef.keepSynced(true);
        let a = params.data;
        this.state = {
            cards: []
        };
    }

    render() {

        this.itemsRef.once('value').then(snapshot => {
            this.setState({cards: snapshot.val()});});

        //console.log(this.state.cards);
        const data = [ 50, 10, 40, 95, -4, -24, 85, 91, 35, 53, -53, 24, 50, -20, -80 ];

        return (
                <AreaChart
                    style={ { height: 200 } }
                    dataPoints={ data }
                    fillColor={ 'rgba(134, 65, 244, 0.2)' }
                    strokeColor={ 'rgb(134, 65, 244)' }
                    contentInset={ { top: 30, bottom: 30 } }
                    //curve={shape.curveNatural}
                />

        );
    }

}

