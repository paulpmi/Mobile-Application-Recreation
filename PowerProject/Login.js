/**
 * Created by paulp on 12/7/2017.
 */
import * as React from "react/cjs/react.production.min";
import {Button, TextInput, View} from "react-native";
import * as firebase from 'firebase';


const firebaseConfig = {
    apiKey: "<your-api-key>",
    authDomain: "<your-auth-domain>",
    databaseURL: "https://mobileapp-50d6f.firebaseio.com/",
    storageBucket: "<your-storage-bucket>",
};
const firebaseApp = firebase.initializeApp(firebaseConfig);

export class RegisterScreen extends React.Component {

}