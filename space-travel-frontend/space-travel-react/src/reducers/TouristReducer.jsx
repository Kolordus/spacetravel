import React, {useState} from "react";


export const TouristReducer = (state, action) => {

    switch (action.type) {
        case 'nameInput':
            console.log(action)
            return {
                ...state,
                name: action.name
            };
    }
}
