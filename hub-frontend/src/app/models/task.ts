export interface Task {
  id: string;
  title: string;
  description: string;
  category: string;
  deadline: string;
  status: string;
  startTime: Date;
  endTime: Date;
  totalTimeSpend: number;
}
