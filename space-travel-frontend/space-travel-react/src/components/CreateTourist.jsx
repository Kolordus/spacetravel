import React, {useReducer} from "react";
import {TouristReducer} from "../reducers/TouristReducer";


export const CreateTourist = () => {

    const [state, dispatch] = useReducer(TouristReducer, {
        name: '',
        surname: '',
        birthDate: {}
    });


    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            "name": "Reactowy",
            "surname": "Koleś",
            "sex": "MALE",
            "country": "PL"
        })
    };

    const postTourist = () => {
        fetch('http://localhost:8080/api/add-tourist', requestOptions)
            .then(response => response.json())
            .then(data => this.setState({postId: data.id}));
    }


    const handleNameChange = (e) => {
        let value = e.target.value;
        dispatch({type: "nameInput", value});
    }

    return (
        <div>
            <input placeholder='name' onChange={handleNameChange} style={{flex: 1}}/>
            <input placeholder='surname' style={{flex: 1}}/>
        </div>
    )
}
