import React, {useEffect, useState} from "react";
import {Button, Card, Col, Container, Image, Nav, Navbar, Pagination, Row} from "react-bootstrap";
import {Link} from "react-router-dom";
import {readAllMusicBandsByPageAndSize} from "../../service/MusicBandService";

const ReadAllMusicBandsComponent = () => {
    const [musicBands, setMusicBands] = useState([]);
    const [totalPages, setTotalPages] = useState(1);
    const [currentPage, setCurrentPage] = useState(0);
    const size = 9;

    const fetchMusicBands = async (page) => {
        try {
            const {data} = await readAllMusicBandsByPageAndSize(page, size);

            setMusicBands(data.content);
            setTotalPages(data.totalPages);
        } catch (error) {
            console.error("Error fetching music bands:", error);
        }
    };

    useEffect(() => {
        fetchMusicBands(currentPage);
    }, [currentPage]);

    const handlePreviousPage = () => {
        if (currentPage > 0) {
            setCurrentPage(currentPage - 1);
        }
    };

    const handleNextPage = () => {
        if (currentPage < totalPages - 1) {
            setCurrentPage(currentPage + 1);
        }
    };

    return (
        <>
            <Navbar bg="primary" data-bs-theme="dark">
                <Container>
                    <Navbar.Brand>Music Band Work Coordination System</Navbar.Brand>

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

                        <Navbar.Text style={{color: "white"}}>
                            Serhii Yatsuk
                        </Navbar.Text>
                    </Nav>
                </Container>
            </Navbar>

            <Container className="my-4" style={{maxWidth: "750px"}}>
                <Row>
                    <Col><h2 className="mb-4">Music Bands</h2></Col>
                    <Col className="d-flex flex-column align-items-end">
                        <Button as={Link} to={"/music-bands/create"} variant="success">Create</Button>
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

export default ReadAllMusicBandsComponent;
