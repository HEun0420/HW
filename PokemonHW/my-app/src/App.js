import {BrowserRouter, Routes, Route} from 'react-router-dom';
import Layout from './layouts/Layouts';
import Main from './pages/Main';
import Pokemons from './pages/Pokemons';



function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Layout/>}>
          <Route index element={<Main/>}/>
          <Route path="/pokemons" element={<Pokemons/>}/>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
