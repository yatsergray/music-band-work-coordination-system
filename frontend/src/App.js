import { Route, Routes } from "react-router-dom";
import ReadAllMusicBandsComponent from "./component/music-band/ReadAllMusicBandsComponent";
import CreateMusicBandComponent from "./component/music-band/CreateMusicBandComponent";

function App() {
  return (
    <Routes>
      <Route path="/" element={<ReadAllMusicBandsComponent />} />
      <Route
        path="/music-bands/create"
        element={<CreateMusicBandComponent />}
      />
    </Routes>
  );
}

export default App;
