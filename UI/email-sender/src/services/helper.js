import axios from "axios";

// backend base url
const baseURL='http://localhost:8080/api/v1';

export const customAxios=axios.create({
  baseURL: baseURL,
})