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

export interface PollCreation {
  question: string;
  options: string[];
}

export interface Vote {
  id: number;
  pollId: number;
  optionId: number;
  createdAt: string;
}

export interface VoteCreation {
  optionId: number;
}
