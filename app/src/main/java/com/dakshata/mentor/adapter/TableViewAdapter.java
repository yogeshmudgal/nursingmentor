package com.dakshata.mentor.adapter;

/**
 * Created by Rakesh Prajapat on 17/01/19
 * Copyright (c) 2019. All rights reserved by mobikode studio.
 * Last modified 3:01 PM
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dakshata.mentor.R;
import com.dakshata.mentor.listener.CustomOnItemClickListner;
import com.dakshata.mentor.models.CompetencyTrackingDto;
import java.util.List;

public class TableViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    List<CompetencyTrackingDto> List;
private CustomOnItemClickListner listner;


    public TableViewAdapter(List<CompetencyTrackingDto> List,CustomOnItemClickListner listner) {
        this.List = List;
        this.listner = listner;

    }
    @Override
    public int getItemCount() {
        return List.size()+1; // one more to add header row
    }

    @Override
    public void onClick(View v) {

    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,
                tv12,tv13,tv14;


        public RowViewHolder(View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            tv5 = itemView.findViewById(R.id.tv5);
            tv6 = itemView.findViewById(R.id.tv6);
            tv7 = itemView.findViewById(R.id.tv7);
            tv8 = itemView.findViewById(R.id.tv8);
            tv9 = itemView.findViewById(R.id.tv9);
            tv10 = itemView.findViewById(R.id.tv10);
            tv11 = itemView.findViewById(R.id.tv11);
            tv12 = itemView.findViewById(R.id.tv12);
            tv13 = itemView.findViewById(R.id.tv13);
            tv14 = itemView.findViewById(R.id.tv14);

        }
    }
    @Override
    public int getItemViewType(int position) {
        if(position<11){
            return 0;
        }
        else{
            return 2;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 0:
                itemView = LayoutInflater.
                        from(parent.getContext()).
                        inflate(R.layout.table_list_item, parent, false);

                return new RowViewHolder(itemView);

            case 2:
                itemView = LayoutInflater.
                        from(parent.getContext()).
                        inflate(R.layout.table_list_item_footer, parent, false);
                return new ViewHolder2(itemView);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                RowViewHolder rowViewHolder = (RowViewHolder) holder;

                int rowPos = rowViewHolder.getAdapterPosition();

                if (rowPos == 0) {
                    rowViewHolder.tv1.setText("Sr.No");
                    rowViewHolder.tv2.setText("Name of Provider");
                    rowViewHolder.tv3.setText("Cadre(OBG/Doctor)");
                    rowViewHolder.tv4.setText("GA Estimation");
                    rowViewHolder.tv5.setText("PV Examination");
                    rowViewHolder.tv6.setText("AMTSL");
                    rowViewHolder.tv7.setText("Partograph");
                    rowViewHolder.tv8.setText("Discharge Counselling");
                    rowViewHolder.tv9.setText("PE/E Management");
                    rowViewHolder.tv10.setText("PPH Management");
                    rowViewHolder.tv11.setText("NBR");
                    rowViewHolder.tv12.setText("Small Size Baby");
                    rowViewHolder.tv13.setText("Competency of Skills(out of 9)");
                    rowViewHolder.tv14.setText("Provider Ranking");
                } else {
                    rowViewHolder.tv1.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv2.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv3.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv4.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv5.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv6.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv7.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv8.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv9.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv10.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv11.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv12.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv13.setBackgroundResource(R.drawable.table_content_cell_bg);
                    rowViewHolder.tv14.setBackgroundResource(R.drawable.table_content_cell_bg);

/*
                    rowViewHolder.tv1.setText(List.get(rowPos-1).getTv1());
                    rowViewHolder.tv2.setText(List.get(rowPos-1).getTv2());
                    rowViewHolder.tv3.setText(List.get(rowPos-1).getTv3());
                    rowViewHolder.tv4.setText(List.get(rowPos-1).getTv4());
                    rowViewHolder.tv5.setText(List.get(rowPos-1).getTv5());
                    rowViewHolder.tv6.setText(List.get(rowPos-1).getTv6());
                    rowViewHolder.tv7.setText(List.get(rowPos-1).getTv7());
                    rowViewHolder.tv8.setText(List.get(rowPos-1).getTv8());
                    rowViewHolder.tv9.setText(List.get(rowPos-1).getTv9());
                    rowViewHolder.tv10.setText(List.get(rowPos-1).getTv10());
                    rowViewHolder.tv11.setText(List.get(rowPos-1).getTv11());
                    rowViewHolder.tv12.setText(List.get(rowPos-1).getTv12());
                    rowViewHolder.tv13.setText(List.get(rowPos-1).getTv13());
                    rowViewHolder.tv14.setText(List.get(rowPos-1).getTv14());*/


                    ((RowViewHolder) holder).tv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv2");
                        }
                    });((RowViewHolder) holder).tv3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv3");
                        }
                    });((RowViewHolder) holder).tv4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv4");
                        }
                    });((RowViewHolder) holder).tv5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv5");
                        }
                    });((RowViewHolder) holder).tv6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv6");
                        }
                    });((RowViewHolder) holder).tv7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv7");
                        }
                    });((RowViewHolder) holder).tv8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv8");
                        }
                    });((RowViewHolder) holder).tv9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv9");
                        }
                    });((RowViewHolder) holder).tv10.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv10");
                        }
                    });((RowViewHolder) holder).tv11.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv11");
                        }
                    });((RowViewHolder) holder).tv12.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv12");
                        }
                    });((RowViewHolder) holder).tv13.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv13");
                        }
                    });((RowViewHolder) holder).tv14.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.OnClick((position-1),"tv14");
                        }
                    });

                }
                break;

            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2)holder;
                // Content Cells. Content appear here
                viewHolder2.tv1.setBackgroundResource(R.drawable.table_content_cell_bg);
                viewHolder2.tv3.setBackgroundResource(R.drawable.table_content_cell_bg);
                viewHolder2.tv4.setBackgroundResource(R.drawable.table_content_cell_bg);
                viewHolder2.tv5.setBackgroundResource(R.drawable.table_content_cell_bg);
                viewHolder2.tv6.setBackgroundResource(R.drawable.table_content_cell_bg);
                viewHolder2.tv7.setBackgroundResource(R.drawable.table_content_cell_bg);
                viewHolder2.tv8.setBackgroundResource(R.drawable.table_content_cell_bg);
                viewHolder2.tv9.setBackgroundResource(R.drawable.table_content_cell_bg);
                viewHolder2.tv10.setBackgroundResource(R.drawable.table_content_cell_bg);
                viewHolder2.tv11.setBackgroundResource(R.drawable.table_content_cell_bg);
                viewHolder2.tv12.setBackgroundResource(R.drawable.table_content_cell_bg);
                viewHolder2.tv13.setBackgroundResource(R.drawable.table_content_cell_bg);
                viewHolder2.tv14.setBackgroundResource(R.drawable.table_content_cell_bg);

                viewHolder2.tv1.setText("Overall Competency at Facility");

               /* viewHolder2.tv3.setText(List.get(position-1).getTv3());
                viewHolder2.tv4.setText(List.get(position-1).getTv4());
                viewHolder2.tv5.setText(List.get(position-1).getTv5());
                viewHolder2.tv6.setText(List.get(position-1).getTv6());
                viewHolder2.tv7.setText(List.get(position-1).getTv7());
                viewHolder2.tv8.setText(List.get(position-1).getTv8());
                viewHolder2.tv9.setText(List.get(position-1).getTv9());
                viewHolder2.tv10.setText(List.get(position-1).getTv10());
                viewHolder2.tv11.setText(List.get(position-1).getTv11());
                viewHolder2.tv12.setText(List.get(position-1).getTv12());
                viewHolder2.tv13.setText(List.get(position-1).getTv13());
                viewHolder2.tv14.setText(List.get(position-1).getTv14());*/

                viewHolder2.tv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listner.OnClick((position-1),"tv3");
                    }
                });viewHolder2.tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.OnClick((position-1),"tv4");
                }
            });viewHolder2.tv5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.OnClick((position-1),"tv5");
                }
            });viewHolder2.tv6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.OnClick((position-1),"tv6");
                }
            });viewHolder2.tv7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.OnClick((position-1),"tv7");
                }
            });viewHolder2.tv8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.OnClick((position-1),"tv8");
                }
            });viewHolder2.tv9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.OnClick((position-1),"tv9");
                }
            });viewHolder2.tv10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.OnClick((position-1),"tv10");
                }
            });viewHolder2.tv11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.OnClick((position-1),"tv11");
                }
            });viewHolder2.tv12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.OnClick((position-1),"tv12");
                }
            });viewHolder2.tv13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.OnClick((position-1),"tv13");
                }
            });viewHolder2.tv14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.OnClick((position-1),"tv14");
                }
            });

                break;
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        protected TextView tv1,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,
                tv12,tv13,tv14;


        public ViewHolder2(View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tv1);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            tv5 = itemView.findViewById(R.id.tv5);
            tv6 = itemView.findViewById(R.id.tv6);
            tv7 = itemView.findViewById(R.id.tv7);
            tv8 = itemView.findViewById(R.id.tv8);
            tv9 = itemView.findViewById(R.id.tv9);
            tv10 = itemView.findViewById(R.id.tv10);
            tv11 = itemView.findViewById(R.id.tv11);
            tv12 = itemView.findViewById(R.id.tv12);
            tv13 = itemView.findViewById(R.id.tv13);
            tv14 = itemView.findViewById(R.id.tv14);

        }
    }
}