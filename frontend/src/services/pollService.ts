import axios from "axios";
import { Poll, VoteCreation } from "../types/poll";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

export const pollService = {
  /**
   * Get the currently active poll
   */
  getActivePoll: async (): Promise<Poll> => {
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
  submitVote: async (voteData: VoteCreation): Promise<Poll> => {
    try {
      const response = await axios.post<Poll>(
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
