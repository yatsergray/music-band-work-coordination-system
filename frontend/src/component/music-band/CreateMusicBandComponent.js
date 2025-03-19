import React, { useEffect, useState } from "react";
import { createMusicBand } from "../../service/MusicBandService";
import { Form, Button, Card } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from "react-router-dom";

const CreateMusicBandComponent = () => {
  const [musicBandCreateRequest, setMusicBandCreateRequest] = useState({
    name: "",
  });
  const [nameValidationErrorMessage, setNameValidationErrorMessage] =
    useState("");
  const navigate = useNavigate();

  const onChange = (event) => {
    setMusicBandCreateRequest({
      ...musicBandCreateRequest,
      [event.target.name]: event.target.value,
    });

    console.log(musicBandCreateRequest);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const { data: musicBandDTO } = await createMusicBand(
        musicBandCreateRequest
      );

      console.log(musicBandDTO);

      setMusicBandCreateRequest({ name: "" });
      setNameValidationErrorMessage("");

      navigate("/");
    } catch (error) {
      console.log(error);

      if (error.response?.data) {
        if (error.response?.data.name) {
          setNameValidationErrorMessage(error.response?.data.name);
        }
      }
    }
  };

  useEffect(() => {
    if (nameValidationErrorMessage) {
      console.log(
        `Name validation error message: ${nameValidationErrorMessage}`
      );
    }
  }, [nameValidationErrorMessage]);

  return (
    <Card
      className="mx-auto mt-5"
      style={{ maxWidth: "400px", padding: "20px" }}
    >
      <Card.Body>
        <h2 className="mb-4">Create Music Band</h2>
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
        </Form>
      </Card.Body>
    </Card>
  );
};

export default CreateMusicBandComponent;
