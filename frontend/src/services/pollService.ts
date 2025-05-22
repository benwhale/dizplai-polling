import axios from "axios";
import {
  PollResponseDTO,
  VoteCreationDTO,
} from "../types/poll";

const API_BASE_URL = "http://localhost:8081"; // TODO move to env

export const pollService = {
  /**
   * Get the currently active poll
   */
  getActivePoll: async (): Promise<PollResponseDTO> => {
    try {
      const response = await axios.get(`${API_BASE_URL}/polls/active`);
      return response.data;
    } catch (error) {
      console.error("Error getting active poll:", error);
      throw error;
    }
  },

  /**
   * Submit a vote for the active poll
   */
  submitVote: async (voteData: VoteCreationDTO): Promise<PollResponseDTO> => {
    try {
      const response = await axios.post<PollResponseDTO>(
        `${API_BASE_URL}/votes/`,
        voteData
      );
      return response.data;
    } catch (error) {
      console.error("Error submitting vote:", error);
      throw error;
    }
  },
};
