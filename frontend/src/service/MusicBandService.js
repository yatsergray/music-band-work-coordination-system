import axios from "axios";

const API_URL = "http://localhost:8080/api/v1/music-bands";

export async function createMusicBand(musicBandCreateRequest) {
    return await axios.post(API_URL, musicBandCreateRequest);
}

export async function readAllMusicBandsByPageAndSize(page, size) {
    return await axios.get(`${API_URL}?page=${page}&size=${size}`);
}

export async function readMusicBandById(musicBandId) {
    return await axios.get(`${API_URL}/${musicBandId}`);
}

export async function updateMusicBandById(musicBandId, musicBandUpdateRequest) {
    return await axios.put(`${API_URL}/${musicBandId}`, musicBandUpdateRequest);
}

export async function deleteMusicBandById(musicBandId) {
    return await axios.delete(`${API_URL}/${musicBandId}`);
}
