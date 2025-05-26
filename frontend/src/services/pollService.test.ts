import axios from "axios";
import { pollService } from "./pollService";

jest.mock("axios");
const mockedAxios = axios as jest.Mocked<typeof axios>;

// Mock environment variable
process.env.REACT_APP_API_BASE_URL = "http://localhost:8081";

describe("pollService", () => {
  // Suppress console.error during tests
  const originalConsoleError = console.error;

  beforeEach(() => {
    jest.clearAllMocks();
  });

  beforeAll(() => {
    console.error = jest.fn();
  });

  afterAll(() => {
    console.error = originalConsoleError;
  });

  describe("getActivePoll", () => {
    it("returns poll data on successful request", async () => {
      const mockPoll = {
        id: 1,
        question: "Test Question?",
        options: [
          { id: 1, name: "Option 1", voteCount: 0 },
          { id: 2, name: "Option 2", voteCount: 0 },
        ],
        active: true,
        createdAt: "2025-01-01T00:00:00Z",
      };

      mockedAxios.get.mockResolvedValue({ data: mockPoll });

      const result = await pollService.getActivePoll();

      expect(mockedAxios.get).toHaveBeenCalledWith(
        "http://localhost:8081/polls/active"
      );
      expect(result).toEqual(mockPoll);
    });

    it("throws error on failed request", async () => {
      const errorMessage = "Network Error";
      mockedAxios.get.mockRejectedValue(new Error(errorMessage));

      await expect(pollService.getActivePoll()).rejects.toThrow(errorMessage);
    });
  });

  describe("submitVote", () => {
    it("submits vote and returns updated poll", async () => {
      const mockUpdatedPoll = {
        id: 1,
        question: "Test Question?",
        options: [
          { id: 1, name: "Option 1", voteCount: 1 },
          { id: 2, name: "Option 2", voteCount: 0 },
        ],
        active: true,
        createdAt: "2025-01-01T00:00:00Z",
      };

      mockedAxios.post.mockResolvedValue({ data: mockUpdatedPoll });

      const result = await pollService.submitVote({ optionId: 1 });

      expect(mockedAxios.post).toHaveBeenCalledWith(
        "http://localhost:8081/votes/",
        { optionId: 1 }
      );
      expect(result).toEqual(mockUpdatedPoll);
    });

    it("throws error on failed vote submission", async () => {
      const errorMessage = "Vote submission failed";
      mockedAxios.post.mockRejectedValue(new Error(errorMessage));

      await expect(pollService.submitVote({ optionId: 1 })).rejects.toThrow(
        errorMessage
      );
    });
  });
});
