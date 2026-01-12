package com.birich.task_tracker.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description; 

    @OneToMany(mappedBy="project", cascade=CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable =  false)
    private User owner;
}
    