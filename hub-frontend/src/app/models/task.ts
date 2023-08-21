export interface Task {
  id: string;
  userId: string;
  title: string;
  description: string;
  category: string;
  deadline: string;
  status: string;
  startTime: Date;
  endTime: Date;
  totalTimeSpent: number;
}
