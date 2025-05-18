export interface PollOption {
  id: number;
  name: string;
  voteCount: number;
}

export interface Poll {
  id: number;
  question: string;
  options: PollOption[];
  active: boolean;
  createdAt: string;
}

export interface PollCreationDTO {
  question: string;
  options: string[];
}

export interface PollResponseDTO extends Poll {}

export interface Vote {
  id: number;
  pollId: number;
  optionId: number;
  createdAt: string;
}

export interface VoteCreationDTO {
  optionId: number;
}

export interface VoteResponseDTO extends Vote {}
