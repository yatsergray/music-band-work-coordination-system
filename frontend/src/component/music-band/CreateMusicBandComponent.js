import React, {useEffect, useState} from "react";
import {Alert, Button, Card, Col, Container, Form, Image, Nav, Navbar, Row} from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import {Link, useNavigate} from "react-router-dom";
import {createMusicBand} from "../../service/MusicBandService";

const CreateMusicBandComponent = () => {
    const [musicBandCreateRequest, setMusicBandCreateRequest] = useState({name: ""});
    const [nameValidationErrorMessage, setNameValidationErrorMessage] = useState("");
    const [musicBandAlreadyExistsErrorMessage, setMusicBandAlreadyExistsErrorMessage] = useState("");
    const navigate = useNavigate();

    const onChange = (event) => {
        setMusicBandCreateRequest({...musicBandCreateRequest, [event.target.name]: event.target.value,});
    };

    const refreshErrorMessages = () => {
        setNameValidationErrorMessage("");
        setMusicBandAlreadyExistsErrorMessage("");
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            await createMusicBand(musicBandCreateRequest);

            refreshErrorMessages();
            setMusicBandCreateRequest({name: ""});

            navigate("/");
        } catch (error) {
            refreshErrorMessages();

            if (error.response.data.name) {
                setNameValidationErrorMessage(error.response.data.name);
            }

            if (error.response.status === 409) {
                setMusicBandAlreadyExistsErrorMessage(error.response.data);
            }
        }
    };

    useEffect(() => {
    }, [nameValidationErrorMessage]);

    return (
        <>
            <Navbar bg="primary" data-bs-theme="dark">
                <Container>
                    <Navbar.Brand>Music Band Work Coordination System</Navbar.Brand>

                    <Nav className="me-auto">
                        <Nav.Link as={Link} to={"/"} style={{color: "white", textDecoration: "none"}}>
                            Home
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

                        <Navbar.Text style={{color: "white"}}>
                            Serhii Yatsuk
                        </Navbar.Text>
                    </Nav>
                </Container>
            </Navbar>

            <Container className="my-4" style={{maxWidth: "400px"}}>
                <Row>
                    <Col><h2 className="mb-4">Create Music Band</h2></Col>
                </Row>

                <Card
                    className="mx-auto mt-1"
                    style={{maxWidth: "400px", padding: "20px"}}
                >
                    <Card.Body>
                        <Form onSubmit={handleSubmit}>
                            <Form.Group className="mb-3">
                                <Form.Label>Name</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="name"
                                    placeholder="Enter name"
                                    value={musicBandCreateRequest.name}
                                    onChange={onChange}
                                    required
                                    isInvalid={!!nameValidationErrorMessage}
                                />
                                {nameValidationErrorMessage && (
                                    <Form.Control.Feedback type="invalid">
                                        {nameValidationErrorMessage}
                                    </Form.Control.Feedback>
                                )}
                            </Form.Group>

                            <Button variant="primary" type="submit">
                                Create
                            </Button>

                            {musicBandAlreadyExistsErrorMessage && (
                                <Alert variant="danger" className="mt-3">
                                    {musicBandAlreadyExistsErrorMessage}
                                </Alert>
                            )}
                        </Form>
                    </Card.Body>
                </Card>
            </Container>
        </>
    );
};

export default CreateMusicBandComponent;
