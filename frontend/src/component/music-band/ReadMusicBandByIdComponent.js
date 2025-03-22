import React, {useEffect, useState} from "react";
import {Button, Card, Col, Container, Image, Nav, Navbar, Pagination, Row} from "react-bootstrap";
import {Link, useParams} from "react-router-dom";
import {readMusicBandById} from "../../service/MusicBandService";

const ReadMusicBandById = () => {
    const {musicBandId} = useParams();
    const [musicBand, setMusicBand] = useState({
        id: "",
        name: "",
        createdAt: "",
        invitations: [],
        musicBandUsers: [],
        chats: [],
        songs: [],
        events: []
    });
    const [musicBands, setMusicBands] = useState([]);

    const fetchMusicBandById = async () => {
        try {
            const {data} = await readMusicBandById(musicBandId);

            console.log(data);

            setMusicBand(data);
        } catch (error) {
            console.error("Error fetching music bands:", error);
        }
    };

    const fetchEvents = async (page) => {
        try {
            const {data} = await readMusicBandById(musicBandId);

            console.log(data);

            setMusicBand(data);
        } catch (error) {
            console.error("Error fetching music bands:", error);
        }

    useEffect(() => {
        fetchMusicBandById();
    }, []);

    return (
        <>
            <Navbar bg="primary" data-bs-theme="dark">
                <Container>
                    <Navbar.Brand>Music Band Work Coordination System</Navbar.Brand>

                    <Nav className="me-auto">
                        <Nav.Link as={Link} to={"/"} style={{color: "white", textDecoration: "none"}}>
                            Home
                        </Nav.Link>
                        <Nav.Link as={Link} to={"/songs"} style={{color: "white", textDecoration: "none"}}>
                            Songs
                        </Nav.Link>
                        <Nav.Link as={Link} to={"/chats"} style={{color: "white", textDecoration: "none"}}>
                            Chats
                        </Nav.Link>
                        <Nav.Link as={Link} to={"/users"} style={{color: "white", textDecoration: "none"}}>
                            Users
                        </Nav.Link>
                        <Nav.Link as={Link} to={"/invitations"} style={{color: "white", textDecoration: "none"}}>
                            Invitations
                        </Nav.Link>
                    </Nav>

                    <Nav className="d-flex align-items-center">
                        <Nav.Link as={Link} to={"/users/a4a61ec3-5df1-4b07-ba0f-39de06722899"}>
                            <Image
                                src="https://www.w3schools.com/howto/img_avatar.png"
                                roundedCircle
                                width="40"
                                height="40"
                                alt="User Avatar"
                            />
                        </Nav.Link>

                        <div className="d-flex flex-column" style={{color: "white"}}>
                            <span>Serhii Yatsuk</span> {/*13*/}
                            <span style={{fontSize: "0.9rem"}}>{musicBand.name.substring(0, 10)}</span>
                        </div>
                    </Nav>
                </Container>
            </Navbar>

            <Container className="my-4" style={{maxWidth: "750px"}}>
                <Row>
                    <Col><h2 className="mb-4">Music Bands</h2></Col>
                    <Col className="d-flex flex-column align-items-end">
                        <Button as={Link} to={"/events/create"} variant="success">Create</Button>
                    </Col>
                </Row>

                <Row className="g-3">
                    {musicBands.map((musicBand) => (
                        <Col
                            sm={12}
                            md={6}
                            lg={4}
                            key={musicBand.id}
                            style={{maxWidth: "250px"}}
                        >
                            <Card className="mx-auto" style={{maxWidth: "250px"}}>
                                <Card.Header as="h5">{musicBand.name.substring(0, 16)}</Card.Header>

                                <Card.Body>
                                    <Button as={Link} to={`/music-bands/${musicBand.id}`} variant="primary">
                                        Go to
                                    </Button>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>

                <div className="d-flex justify-content-center my-4">
                    <Pagination>
                        <Pagination.Prev
                            onClick={handlePreviousPage}
                            disabled={currentPage === 0}
                        />

                        {[...Array(totalPages)].map((_, index) => (
                            <Pagination.Item
                                key={index}
                                active={index === currentPage}
                                onClick={() => setCurrentPage(index)}
                            >
                                {index + 1}
                            </Pagination.Item>
                        ))}

                        <Pagination.Next
                            onClick={handleNextPage}
                            disabled={currentPage === totalPages - 1}
                        />
                    </Pagination>
                </div>
            </Container>
        </>
    );
};

export default ReadMusicBandById;
