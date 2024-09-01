const initialState =[
    {
        id: 0,
        name: ''
    }
]

const GET_POKEMONS = 'pokemons/GET_POKEMONS'

const actions = createActions({
    [GET_POKEMONS]: () => {}
})

const pokemonReducer = handleActions(
    {
        [GET_POKEMONS]: (state, {payload}) => {
            console.log('payload:', payload);

            return payload;
        }
    }, initialState
)

export default pokemonReducer;
