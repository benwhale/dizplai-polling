import axios from "axios";
import {
  PollResponseDTO,
  VoteCreationDTO,
  VoteResponseDTO,
} from "../types/poll";

const API_BASE_URL = "http://localhost:8081"; // TODO move to env

export const pollService = {
  /**
   * Get the currently active poll
   */
  getActivePoll: async (): Promise<PollResponseDTO> => {
    const response = await axios.get(`${API_BASE_URL}/polls/active`);
    return response.data;
  },

  /**
   * Submit a vote for the active poll
   */
  submitVote: async (voteData: VoteCreationDTO): Promise<VoteResponseDTO> => {
    const response = await axios.post(`${API_BASE_URL}/votes/`, voteData);
    return response.data;
  },
};
