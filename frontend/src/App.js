import {Route, Routes} from "react-router-dom";
import ReadAllMusicBandsComponent from "./component/music-band/ReadAllMusicBandsComponent";
import CreateMusicBandComponent from "./component/music-band/CreateMusicBandComponent";
import ReadMusicBandByIdComponent from "./component/music-band/ReadMusicBandByIdComponent";

function App() {
    return (
        <Routes>
            <Route path="/music-bands/create" element={<CreateMusicBandComponent/>}/>
            <Route path="/music-bands/:musicBandId" element={<ReadMusicBandByIdComponent/>}/>
            <Route path="/" element={<ReadAllMusicBandsComponent/>}/>
        </Routes>
    );
}

export default App;
